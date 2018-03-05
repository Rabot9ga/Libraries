package ru.sbt.util.concurrent_util;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConcurrentUtil {

    public static <K, V> V putInMap(ConcurrentMap<K, V> map, K key, Supplier<V> valueSupplier) {
        return putInMap(map, key, valueSupplier, null);
    }

    public static <K, V> V putInMap(ConcurrentMap<K, V> map, K key, Supplier<V> valueSupplier, Consumer<V> actionOnPut) {
        V returnValue = map.get(key);
        if (returnValue == null) {
            V value = valueSupplier.get();
            V ifAbsent = map.putIfAbsent(key, value);
            if (ifAbsent == null) {
                returnValue = value;
                if (actionOnPut != null) {
                    actionOnPut.accept(returnValue);
                }
            } else {
                returnValue = ifAbsent;
            }
        }
        return returnValue;
    }
}
