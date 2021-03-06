package ru.sbt.util.pcaccessapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataRs<T> {

    private Status status;
    private T payload;
    private String errorMessage;


    public static <T> DataRs<T> success(T payload) {
        return DataRs.<T>builder()
                .status(Status.SUCCESS)
                .payload(payload)
                .errorMessage("")
                .build();
    }

    public static <T> DataRs<T> error(String errorMessage) {
        return DataRs.<T>builder()
                .status(Status.ERROR)
                .errorMessage(errorMessage)
                .build();
    }

    public static <T> DataRs<T> error(String errorMessage, Throwable t) {
        String errorMessageRs = errorMessage + "\n" + t.toString();

        return DataRs.<T>builder()
                .status(Status.ERROR)
                .errorMessage(errorMessageRs)
                .build();
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}
