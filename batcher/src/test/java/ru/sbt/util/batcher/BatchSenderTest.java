package ru.sbt.util.batcher;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

public class BatchSenderTest {
    private TestInterface testInterface;


    @BeforeMethod
    public void setUp() throws Exception {
        testInterface = mock(TestInterface.class);
    }

    @Test
    public void testPutBatch() throws Exception {
        TestInterface testInterface = mock(TestInterface.class);
        BatchSender batchSender = new BatchSender<String>(100, 10, TimeUnit.SECONDS, testInterface::testMethod);
        List<String> stringList = getStrings(100);

        stringList.forEach(batchSender::put);

        verify(testInterface, after(100)).testMethod(stringList);
    }

    @Test
    public void testPutSome() throws Exception {
        TestInterface testInterface = mock(TestInterface.class);
        BatchSender batchSender = new BatchSender<String>(100, 300, TimeUnit.MILLISECONDS, testInterface::testMethod);
        List<String> stringList = getStrings(10);

        stringList.forEach(batchSender::put);

        verify(testInterface, after(400).only()).testMethod(stringList);
    }

    @Test
    public void testPutExtraBatch() throws Exception {
        TestInterface testInterface = mock(TestInterface.class);
        List<String> stringList = getStrings(199);
        BatchSender batchSender = new BatchSender<String>(100, 300, TimeUnit.MILLISECONDS, testInterface::testMethod);

        stringList.forEach(batchSender::put);

        verify(testInterface, after(20)).testMethod(stringList.subList(0, 100));
        verify(testInterface, after(600).atLeastOnce()).testMethod(stringList.subList(100, 199));
    }

    @Test
    public void testFlush() throws Exception {
        TestInterface testInterface = mock(TestInterface.class);
        List<String> stringList = getStrings(10);
        BatchSender batchSender = new BatchSender<String>(100, 300, TimeUnit.MILLISECONDS, testInterface::testMethod);

        stringList.forEach(batchSender::put);
        batchSender.flush();

        verify(testInterface).testMethod(stringList);
    }

    private List<String> getStrings(int num) {
        return IntStream.range(0, num)
                .mapToObj(value -> "testString" + value)
                .collect(toList());
    }
}