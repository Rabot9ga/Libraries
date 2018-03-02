package ru.sbt.util.jdbclib.util;

import org.springframework.util.CustomizableThreadCreator;

import java.util.concurrent.ThreadFactory;

public class WorkerThreadFactory extends CustomizableThreadCreator implements ThreadFactory {

    public WorkerThreadFactory() {
        super();
    }

    public WorkerThreadFactory(String threadNamePrefix) {
        super(threadNamePrefix);
    }

    @Override
    public Thread newThread(Runnable r) {
        return createThread(r);
    }
}
