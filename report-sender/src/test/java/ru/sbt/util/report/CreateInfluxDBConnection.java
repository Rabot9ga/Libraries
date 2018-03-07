package ru.sbt.util.report;

import org.influxdb.InfluxDB;
import org.testng.annotations.Test;
import ru.sbt.util.report.influx.InfluxDBConnection;

public class CreateInfluxDBConnection {

    @Test(enabled = true)
    public void initializeInstance() {

        InfluxDB instance = InfluxDBConnection.getInstance();

    }

}
