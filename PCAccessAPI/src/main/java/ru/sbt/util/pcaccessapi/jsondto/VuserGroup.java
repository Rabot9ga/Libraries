package ru.sbt.util.pcaccessapi.jsondto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Script {

        @SerializedName("ID")
        private int id;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Host {

        @SerializedName("Name")
        private String name;

        @SerializedName("Type")
        private String type;

    }

}

