package ru.sbt.util.pcaccessapi.jsondto.scenario;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Ramp {
    @SerializedName("Vusers")
    private Integer vusers;
    @SerializedName("TimeInterval")
    private TimeInterval timeInterval;
}
