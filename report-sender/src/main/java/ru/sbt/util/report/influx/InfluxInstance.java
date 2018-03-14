package ru.sbt.util.report.influx;

import okhttp3.OkHttpClient;
import org.influxdb.impl.InfluxDBImpl;

public class InfluxInstance extends InfluxDBImpl {

    private String measurement;

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public InfluxInstance(String url, String username, String password, OkHttpClient.Builder client) {
        super(url, username, password, client);
    }

    public InfluxInstance(String url, String username, String password, OkHttpClient.Builder client, String database, String retentionPolicy, ConsistencyLevel consistency) {
        super(url, username, password, client, database, retentionPolicy, consistency);
    }

}
