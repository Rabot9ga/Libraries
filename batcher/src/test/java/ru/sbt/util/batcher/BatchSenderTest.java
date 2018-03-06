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

public class BatchSenderTest {
    private TestInterface testInterface;


    @BeforeMethod
    public void setUp() {
        testInterface = mock(TestInterface.class);
    }

    @Test
    public void testPutBatch() {
        TestInterface testInterface = mock(TestInterface.class);
        BatchConsumer batchConsumer = new BatchConsumer<String>(testInterface::testMethod, 100, 10, TimeUnit.SECONDS);
        List<String> stringList = getStrings(100);

        stringList.forEach(batchConsumer::put);

        verify(testInterface, after(100)).testMethod(stringList);
    }

    @Test
    public void testPutSome() {
        TestInterface testInterface = mock(TestInterface.class);
        BatchConsumer batchConsumer = new BatchConsumer<String>(testInterface::testMethod, 100, 300, TimeUnit.MILLISECONDS);
        List<String> stringList = getStrings(10);

        stringList.forEach(batchConsumer::put);

        verify(testInterface, after(400).only()).testMethod(stringList);
    }

    @Test
    public void testPutExtraBatch() {
        TestInterface testInterface = mock(TestInterface.class);
        List<String> stringList = getStrings(199);
        BatchConsumer batchConsumer = new BatchConsumer<String>(testInterface::testMethod, 100, 300, TimeUnit.MILLISECONDS);

        stringList.forEach(batchConsumer::put);

        verify(testInterface, after(20)).testMethod(stringList.subList(0, 100));
        verify(testInterface, after(600).atLeastOnce()).testMethod(stringList.subList(100, 199));
    }

    @Test
    public void testFlush() {
        TestInterface testInterface = mock(TestInterface.class);
        List<String> stringList = getStrings(10);
        BatchConsumer batchConsumer = new BatchConsumer<String>(testInterface::testMethod, 100, 300, TimeUnit.MILLISECONDS);

        stringList.forEach(batchConsumer::put);
        batchConsumer.flush();

        verify(testInterface).testMethod(stringList);
    }

    private List<String> getStrings(int num) {
        return IntStream.range(0, num)
                .mapToObj(value -> "testString" + value)
                .collect(toList());
    }
}