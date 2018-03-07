package ru.sbt.util.report.influx;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Point;
import ru.sbt.util.report.Status;
import ru.sbt.util.report.Transaction;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Slf4j
public class InfluxReportDAO  {

    private InfluxInstance instance = InfluxDBConnection.getInstance();

    public void sendToInflux(Transaction transaction) {
        double respTime = (double) (transaction.getEndTime() - transaction.getStartTime()) / 1000;
        long startTime = transaction.getStartTime() * 1_000_000 + ThreadLocalRandom.current().nextInt(999_999);
        Point point = Point.measurement(instance.getMeasurement())
                .time(startTime, TimeUnit.NANOSECONDS)
                .addField("Response_Time", respTime)
                .tag("ScriptName", transaction.getScriptName())
                .tag("Status", String.valueOf(transaction.getStatus()))
                .build();
        if (instance != null) {
            instance.write(point);
        } else {
            log.error("InfluxDB instance is null");
        }
    }

    // не сохраняем в мапу
    public void sendToInflux(long endTime,
                             long startTime,
                             String scriptName,
                             Status status) {
//        double respTime = (double) (endTime - startTime) / 1000;
        double respTime = endTime - startTime;

        long nanoStartTime = startTime * 1_000_000 + ThreadLocalRandom.current().nextInt(999_999);
        Point point = Point.measurement(instance.getMeasurement())
                .time(nanoStartTime, TimeUnit.NANOSECONDS)
                .addField("Response_Time", respTime)
                .tag("ScriptName", scriptName)
                .tag("Status", String.valueOf(status))
                .build();
        if (instance != null) {
            instance.write(point);
        } else {
            log.error("InfluxDB instance is null");
        }
    }

    //не сохраняем в мапу, но сохраняем с тегом userId
    public void sendToInflux(long endTime,
                             long startTime,
                             String scriptName,
                             String vuserId,
                             Status status) {
        double respTime = (double) (endTime - startTime) / 1000;
        long nanoStartTime = startTime * 1_000_000 + ThreadLocalRandom.current().nextInt(999_999);
        Point point = Point.measurement(instance.getMeasurement())
                .time(nanoStartTime, TimeUnit.NANOSECONDS)
                .addField("Response_Time", respTime)
                .tag("ScriptName", scriptName)
                .tag("userId", vuserId)
                .tag("Status", String.valueOf(status))
                .build();
        if (instance != null) {
            instance.write(point);
        } else {
            log.error("InfluxDB instance is null");
        }
    }

    public void sendLoadToInflux(long time, String scriptName) {
        long nanoStartTime = time * 1_000_000 + ThreadLocalRandom.current().nextInt(999_999);
        Point point = Point.measurement(instance.getMeasurement())
                .time(nanoStartTime, TimeUnit.NANOSECONDS)
                .addField("counter", 1)
                .tag("ScriptName", scriptName)
                .build();
        if (instance != null) {
            instance.write(point);
        } else {
            log.error("InfluxDB instance is null");
        }
    }


}
