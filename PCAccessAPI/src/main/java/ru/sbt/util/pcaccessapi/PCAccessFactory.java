package ru.sbt.util.pcaccessapi;

public class PCAccessFactory {
    public static PCAccess create(String url, String login, String password) {

        return new PCAccessImpl(url, login, password);
    }
}
