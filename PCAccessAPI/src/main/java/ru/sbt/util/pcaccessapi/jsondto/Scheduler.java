package ru.sbt.util.pcaccessapi.jsondto;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

public class Scheduler {

    @SerializedName("Actions")
    private List<Action> actions;

    private static class Ramp {
        @SerializedName("Vusers")
        private Integer vusers;

        @SerializedName("TimeInterval")
        private TimeInterval timeInterval;
    }

    private static class TimeInterval {
        @SerializedName("Seconds")
        private Integer seconds;
        @SerializedName("Minutes")
        private Integer minutes;
        @SerializedName("Hours")
        private Integer hours;
    }

    @AllArgsConstructor
    public class StartGroup extends Action {
        @SerializedName("Type")
        private String type;
    }

    @AllArgsConstructor
    public class Initialize extends Action {
        @SerializedName("Type")
        private String type;
    }

    @AllArgsConstructor
    public class StartVusers extends Action {
        @SerializedName("Type")
        private String type;
        @SerializedName("Vusers")
        private String vusers;
    }

    @AllArgsConstructor
    public class Duration extends Action {
        @SerializedName("Type")
        private String type;

        @SerializedName("TimeInterval")
        private TimeInterval timeInterval;
    }

    @AllArgsConstructor
    public class StopVusers extends Action {
        @SerializedName("Type")
        private String type;

        @SerializedName("Ramp")
        private Ramp ramp;
    }
}

