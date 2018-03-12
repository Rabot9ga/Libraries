package ru.sbt.util.report;

import lombok.extern.slf4j.Slf4j;
import ru.sbt.util.report.influx.InfluxReportDAO;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ReportSender {

    private static InfluxReportDAO dao = new InfluxReportDAO();
    private static ConcurrentHashMap<String, Transaction> transactionMap = new ConcurrentHashMap<>();
    private static final SecureRandom randomUid = new SecureRandom();


    public static void startTransaction(String scriptName, String uid) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Transaction transaction = new Transaction(
                lowerCaseScriptName,
                uid,
                System.currentTimeMillis());
        transactionMap.put(transaction.getNameAndId(), transaction);
        log.info("put transaction - " + transaction + " in map");
        log.info("map size - " + transactionMap.size());
    }

    public static void successEndTransaction(String scriptName, String uid) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Transaction transaction = transactionMap.get(lowerCaseScriptName + uid);

        transaction.setEndTime(System.currentTimeMillis());
        transaction.setStatus(Status.SUCCESS);
        log.info("send transaction - " + transaction + " in Influx");

        dao.sendToInflux(transaction);

        transactionMap.remove(transaction.getNameAndId());
        log.info("transaction - " + transaction + " removed");
        log.info("map size - " + transactionMap.size());
    }

    public static void failEndTransaction(String scriptName, String uid) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Transaction transaction = transactionMap.get(lowerCaseScriptName + uid);
        transaction.setEndTime(System.currentTimeMillis());
        transaction.setStatus(Status.ERROR);
        dao.sendToInflux(transaction);
        transactionMap.remove(transaction.getNameAndId());
    }

    public static void notReceivedEndTransaction(String scriptName, String uid) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Transaction transaction = transactionMap.get(lowerCaseScriptName + uid);
        transaction.setEndTime(System.currentTimeMillis());
        transaction.setStatus(Status.NOT_RECEIVED);
        dao.sendToInflux(transaction);
        transactionMap.remove(transaction.getNameAndId());
    }

    public static void successEndTransaction(String scriptName, long startTime) {
        long endTime = System.currentTimeMillis();
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status success = Status.SUCCESS;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                success);
    }

    public static void failEndTransaction(String scriptName, long startTime) {
        long endTime = System.currentTimeMillis();
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status error = Status.ERROR;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                error);
    }

    public static void notReceivedEndTransaction(String scriptName, long startTime) {
        long endTime = System.currentTimeMillis();
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status notReceived = Status.NOT_RECEIVED;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                notReceived);
    }

    public static void successEndTransaction(String scriptName, long startTime, String userId) {
        long endTime = System.currentTimeMillis();
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status success = Status.SUCCESS;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                userId,
                success);
    }

    public static void failEndTransaction(String scriptName, long startTime, String userId) {
        long endTime = System.currentTimeMillis();
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status error = Status.ERROR;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                userId,
                error);
    }

    public static void notReceivedEndTransaction(String scriptName, long startTime, String userId) {
        long endTime = System.currentTimeMillis();
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status notReceived = Status.NOT_RECEIVED;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                userId,
                notReceived);
    }

    public static void successEndTransaction(String scriptName, long startTime, long endTime) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status success = Status.SUCCESS;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                success);
    }

    public static void failEndTransaction(String scriptName, long startTime, long endTime) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status error = Status.ERROR;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                error);
    }

    public static void notReceivedEndTransaction(String scriptName, long startTime, long endTime) {
        String lowerCaseScriptName = scriptName.toLowerCase();
        Status notReceived = Status.NOT_RECEIVED;
        dao.sendToInflux(
                endTime,
                startTime,
                lowerCaseScriptName,
                notReceived);
    }

    public static void sendLoadInfo(long time, String scriptName) {
        String lowerCase = scriptName.toLowerCase();
        dao.sendLoadToInflux(time, lowerCase);
    }


    public static String getUid() {
        return UUID.randomUUID().toString();
    }

}
