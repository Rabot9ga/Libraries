package ru.sbt.util.pcaccessapi.jsondto.scenario;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class Scheduler {
    @SerializedName("Actions")
    private List<Action> actions;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public class StartGroup extends Action {
        @SerializedName("Type")
        private String type;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public class Initialize extends Action {
        @SerializedName("Type")
        private String type;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public class StartVusers extends Action {
        @SerializedName("Type")
        private String type;
        @SerializedName("Vusers")
        private Integer vusers;
        @SerializedName("Ramp")
        private Ramp ramp;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public class Duration extends Action {
        @SerializedName("Type")
        private String type;
        @SerializedName("TimeInterval")
        private TimeInterval timeInterval;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public class StopVusers extends Action {
        @SerializedName("Type")
        private String type;
        @SerializedName("Vusers")
        private Integer vusers;
        @SerializedName("Ramp")
        private Ramp ramp;
    }
}
