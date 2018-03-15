package ru.sbt.util.pcaccessapi.jsondto.scenario;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RuntimeSettings {

    @SerializedName("Pacing")
    private Pacing pacing;

    @SerializedName("ThinkTime")
    private ThinkTime thinkTime;

    @SerializedName("Log")
    private Log log;

    @SerializedName("Scheduler")
    private Scheduler scheduler;

    @Data
    public static class Log {

        @SerializedName("Type")
        private Type type;

        @SerializedName("ParametersSubstituion")
        private Boolean parametersSubstituion;

        @SerializedName("DataReturnedByServer")
        private Boolean dataReturnedByServer;

        @SerializedName("AdvanceTrace")
        private Boolean advanceTrace;

        @SerializedName("LogOptions")
        private Options options;

        @Data
        public static class Options {

            @SerializedName("Type")
            private Type type;

            @SerializedName("CacheSize")
            private Integer cacheSize;

            @AllArgsConstructor
            public enum Type {
                @SerializedName("on error")
                ON_ERROR("on error");

                private final String type;

                @Override
                public String toString() {
                    return type;
                }
            }

        }

        @AllArgsConstructor
        public enum Type {
            @SerializedName("extended")
            EXTENDED("extended");

            private final String type;

            @Override
            public String toString() {
                return type;
            }
        }
    }

    @Data
    public static class Pacing {

        @SerializedName("NumberOfIterations")
        private Integer numberOfIterations;

        @SerializedName("StartNewIteration")
        private StartNewIteration startNewIteration;

        @Data
        public static class StartNewIteration {

            @SerializedName("Type")
            private Type type;

            @SerializedName("DelayOfSeconds")
            private Double delaySeconds;

            @AllArgsConstructor
            public enum Type {
                @SerializedName("fixed interval")
                FIXED_INTERVAL("fixed interval");

                private final String type;

                @Override
                public String toString() {
                    return type;
                }
            }
        }
    }

    @Data
    public static class ThinkTime {

        @SerializedName("Type")
        private Type type;

        @AllArgsConstructor
        public enum Type {
            @SerializedName("replay")
            REPLAY("replay");

            private final String type;

            @Override
            public String toString() {
                return type;
            }
        }
    }

}
