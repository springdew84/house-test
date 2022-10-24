package com.cassey.house.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> void partition(T[] array, final int size, PartitionConsumer<T> consumer) throws Exception {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer must be specified");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }

        if (array != null && array.length > 0) {
            int index = 0;
            List<T> part = new ArrayList<>(size);
            for (T t : array) {
                part.add(t);
                index++;
                if (index % size == 0 && !part.isEmpty()) {
                    consumer.accept(part);
                    part.clear();
                }
            }

            if (!part.isEmpty()) {
                consumer.accept(part);
            }
        }
    }


    /**
     * 将集合分段，并对每个分段执行指定操作
     *
     * @param iterable 集合
     * @param size     每个段的最大数据条数
     * @param consumer 每个段的数据消费者
     * @param <T>
     */
    public static <T> void partition(Iterable<T> iterable, final int size, PartitionConsumer<T> consumer) throws Exception {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer must be specified");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }

        if (iterable != null) {
            int index = 0;
            List<T> part = new ArrayList<>(size);
            for (T t : iterable) {
                part.add(t);
                index++;
                if (index % size == 0 && !part.isEmpty()) {
                    consumer.accept(part);
                    part.clear();
                }
            }

            if (!part.isEmpty()) {
                consumer.accept(part);
            }
        }
    }

    public interface PartitionConsumer<T> {
        void accept(List<T> partition) throws Exception;
    }

}
