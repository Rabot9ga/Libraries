package ru.sbt.util.pcaccessapi.jsondto.run;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Run {

    @Expose
    @SerializedName("TestInstanceID")
    private Integer testInstanceID;
    @Expose
    @SerializedName("TestID")
    private Integer testID;
    @Expose
    @SerializedName("AverageThroughputPerSecond")
    private Double averageThroughputPerSecond;
    @Expose
    @SerializedName("AverageHitsPerSecond")
    private Double averageHitsPerSecond;
    @Expose
    @SerializedName("TotalErrors")
    private Double totalErrors;
    @Expose
    @SerializedName("TotalFailedTransactions")
    private Double totalFailedTransactions;
    @Expose
    @SerializedName("TotalPassedTransactions")
    private Double totalPassedTransactions;
    @Expose
    @SerializedName("MaxVusers")
    private Double maxVusers;
    @Expose
    @SerializedName("EndTime")
    private LocalDateTime endTime;
    @Expose
    @SerializedName("StartTime")
    private LocalDateTime startTime;
    @Expose
    @SerializedName("RunSLAStatus")
    private String runSLAStatus;
    @Expose
    @SerializedName("RunState")
    private String runState;
    @Expose
    @SerializedName("Duration")
    private Integer duration;
    @Expose
    @SerializedName("ID")
    private Integer id;
}
