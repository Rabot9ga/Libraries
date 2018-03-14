package ru.sbt.util.pcaccessapi.jsondto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class VuserGroup {

    @SerializedName("Name")
    private String name;

    @SerializedName("Vusers")
    private double vusers;

    @SerializedName("Script")
    private Script script;

    @SerializedName("Hosts")
    private List<Host> hosts;

    @SerializedName("Scheduler")
    private Scheduler scheduler;

    @SerializedName("RTS")
    private RuntimeSettings runtimeSettings;

    @Data
    public static final class Script {
        @SerializedName("ID")
        private int id;
    }

    @Data
    public static final class Host {
        @SerializedName("Name")
        private String name;
        @SerializedName("Type")
        private String type;
    }

}

