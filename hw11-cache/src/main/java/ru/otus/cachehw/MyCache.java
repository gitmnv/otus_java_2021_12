package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    private final List<HwListener<K, V>> listeners = new ArrayList<>();
    private final WeakHashMap<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notify(key, value, "put");
    }

    @Override
    public void remove(K key) {
        notify(key, cache.remove(key), "remove");
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notify(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notify(K key, V value, String action) {

        for (HwListener<K, V> listener : listeners) {
            Objects.requireNonNull(listener).notify(key, value, action);
        }
    }
}
