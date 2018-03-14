package ru.sbt.util.report;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbt.util.report.influx.InfluxDBConnection;
import ru.sbt.util.report.influx.InfluxInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

@Slf4j
public class WithoutTransactionDdos {

    private InfluxInstance instance;

    @BeforeMethod
    public void flushDB() {
        InputStream resource = InfluxDBConnection.class.getResourceAsStream("/test.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource);
            if (properties == null) {
                throw new ReportSenderException("Cannot load properties");
            }
        } catch (ReportSenderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String measurement = properties.getProperty("influx.measurement");
        String dbName = properties.getProperty("influx.dbName");

        instance = InfluxDBConnection.getInstance(properties);

        String query = String.format("drop measurement %s", measurement);
        QueryResult queryResult = instance.query(new Query(query, dbName));
        if (queryResult.hasError()) {
            log.info(String.format("cannot flush measurement %s from %s",
                    measurement,
                    dbName));
        }
    }

    @Test
    public void ddos() throws InterruptedException {

        int countWrites = 10_000;

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(countWrites);

        Runnable task = () -> {
            String scriptName = Thread.currentThread().getName();
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (ThreadLocalRandom.current().nextInt(3) % 3) {
                case 0:
                    ReportSender.successEndTransaction(scriptName, start, System.currentTimeMillis());
                    break;
                case 1:
                    ReportSender.failEndTransaction(scriptName, start, System.currentTimeMillis());
                    break;
                case 2:
                    ReportSender.notReceivedEndTransaction(scriptName, start, System.currentTimeMillis());
                    break;
            }
            log.info("iteration - {}", countDownLatch.getCount());
            countDownLatch.countDown();
        };

        for (int i = 0; i < countWrites; i++) {
            executorService.submit(task);
        }
        countDownLatch.await();
        TimeUnit.SECONDS.sleep(2);

        QueryResult queryResult = instance.query(new Query("select count(Response_Time) from TEST", "SmokeUCP"));
        Double result = (Double) queryResult.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);
        int count = result.intValue();
        boolean percentAssert = (((99 * countWrites) / 100) < count);

        log.info("writed {} from {} points", count, countWrites);
        assertEquals(
                percentAssert,
                true,
                String.format("writed %s from %s points, Less than 99 percent", count, countWrites));
        assertEquals(count, countWrites, String.format("writed %s from %s points", count, countWrites));

    }

}
