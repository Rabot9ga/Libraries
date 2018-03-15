package ru.sbt.util.pcaccessapi.jsondto.scenario;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TimeInterval {
    @SerializedName("Seconds")
    private Integer seconds;
    @SerializedName("Minutes")
    private Integer minutes;
    @SerializedName("Hours")
    private Integer hours;
}
