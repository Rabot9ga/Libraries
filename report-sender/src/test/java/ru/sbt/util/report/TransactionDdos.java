package ru.sbt.util.report;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class TransactionDdos {


    @Test()
    public void ddos() throws InterruptedException {

        int countWrites = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(countWrites);

        Runnable task = () -> {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (ThreadLocalRandom.current().nextInt(3) % 3) {
                case 0:
                    ReportSender.successEndTransaction(Thread.currentThread().getName(), start);
                    break;
                case 1:
                    ReportSender.failEndTransaction(Thread.currentThread().getName(), start);
                    break;
                case 2:
                    ReportSender.notReceivedEndTransaction(Thread.currentThread().getName(), start);
                    break;
            }
            countDownLatch.countDown();
        };

        for (int i = 0; i < countWrites; i++) {
            executorService.submit(task);
        }

        countDownLatch.await();
    }

}
