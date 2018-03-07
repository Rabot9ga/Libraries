package ru.sbt.util.report;

import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;


public class ReportTest {


    @Test(invocationCount = 500, threadPoolSize = 10, groups = "Transactions")
    public void successEndedTransaction() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String uid = ReportSender.getUid();
        ReportSender.startTransaction("test - successTransaction", uid);

        try {
            Thread.sleep(random.nextInt(100,1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReportSender.successEndTransaction("test - successTransaction", uid);
    }



    @Test(invocationCount = 500, threadPoolSize = 10, groups = "Transactions", enabled = false)
    public void errorEndedTransaction() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String uid = ReportSender.getUid();
        ReportSender.startTransaction("test - errorTransaction", uid);

        try {
            Thread.sleep(random.nextInt(100,1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReportSender.failEndTransaction("test - errorTransaction", uid);

    }

    @Test(invocationCount = 500, threadPoolSize = 10, groups = "Transactions", enabled = false)
    public void receivedTransaction() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String uid = ReportSender.getUid();
        ReportSender.startTransaction("test - ReceivedTransaction", uid);

        try {
            Thread.sleep(random.nextInt(100,1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReportSender.notReceivedEndTransaction("test - ReceivedTransaction", uid);

    }


}
