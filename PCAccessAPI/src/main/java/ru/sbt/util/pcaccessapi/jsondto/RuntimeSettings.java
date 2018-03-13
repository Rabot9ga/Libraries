package ru.sbt.util.pcaccessapi.jsondto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RuntimeSettings {

    @SerializedName("Pacing")
    private Pacing pacing;

    @SerializedName("ThinkTime")
    private ThinkTime thinkTime;

    @SerializedName("Log")
    private Log log;

    @SerializedName("Scheduler")
    private Scheduler scheduler;

    @Getter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

        @Getter
        @ToString
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Options {

            @SerializedName("Type")
            private Type type;

            @SerializedName("CacheSize")
            private Integer cacheSize;

            @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Getter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Pacing {

        @SerializedName("NumberOfIterations")
        private Integer numberOfIterations;

        @SerializedName("StartNewIteration")
        private StartNewIteration startNewIteration;

        @Getter
        @ToString
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        public static class StartNewIteration {

            @SerializedName("Type")
            private Type type;

            @SerializedName("DelayOfSeconds")
            private Double delaySeconds;

            @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Getter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ThinkTime {

        @SerializedName("Type")
        private Type type;

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
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
