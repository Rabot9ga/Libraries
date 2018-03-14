package ru.sbt.util.report.influx;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDBException;
import ru.sbt.util.report.ReportSenderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@Slf4j
public class InfluxDBConnection {

    private static InfluxInstance influxDB = null;

    public static InfluxInstance getInstance() {
        if (influxDB == null) {
            synchronized (InfluxInstance.class) {
                if (influxDB == null) {
                    influxDB = createInstance(null);
                }
            }
        }
        return influxDB;
    }

    // для тестов
    public static InfluxInstance getInstance(Properties properties) {
        if (influxDB == null) {
            synchronized (InfluxInstance.class) {
                if (influxDB == null) {
                    influxDB = createInstance(properties);
                }
            }
        }
        return influxDB;
    }

    private static InfluxInstance createInstance(Properties properties) {
        InputStream resource = InfluxDBConnection.class.getResourceAsStream("/my.properties");

        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(resource);
                if (properties == null)
                    throw new ReportSenderException("Cannot load properties");
            } catch (ReportSenderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String url = properties.getProperty("influx.url");
        String dbName = properties.getProperty("influx.dbName");
        String batch = properties.getProperty("influx.batchActions");
        String flush = properties.getProperty("influx.flushDurationSec");

        String measurement = properties.getProperty("influx.measurement");

        if (!(url == null || dbName == null || batch == null || flush == null || measurement == null
                || url.equals("") || dbName.equals("") || batch.equals("") || flush.equals("") || measurement.equals(""))) {
            influxDB = new InfluxInstance(url, null, null, new OkHttpClient.Builder());
            influxDB.setMeasurement(measurement);
            influxDB.createDatabase(dbName);
            influxDB.setDatabase(dbName);
            int batchActions = Integer.parseInt(batch);
            int flushDurationSec = Integer.parseInt(flush);
            influxDB.enableBatch(batchActions, flushDurationSec, TimeUnit.SECONDS);
            return influxDB;
        } else {
            throw new InfluxDBException("you need set maven properties for using that lib");
        }

    }

}
