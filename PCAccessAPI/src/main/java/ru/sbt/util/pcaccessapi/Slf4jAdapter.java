package ru.sbt.util.pcaccessapi;

import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jAdapter implements HttpLoggingInterceptor.Logger {

    private final Logger log;

    public Slf4jAdapter(Class<?> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void log(String message) {
        log.trace(message);
    }
}
