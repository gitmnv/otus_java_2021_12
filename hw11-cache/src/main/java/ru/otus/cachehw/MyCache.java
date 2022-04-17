package ru.otus.cachehw;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    WeakHashMap<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        System.out.println("cache!!!! " + cache);
        cache.put(key, value);
        notify(key, value, "put");
    }

    @Override
    public void remove(K key) {
        notify(key, cache.remove(key), "remove");
    }

    @Override
    public V get(K key) {
        V valuev = cache.get(key);
        notify(key, valuev, "get");
        return valuev;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        for (int i = 0; i < listeners.size(); i++) {
            var v = listeners.get(i).get();
            if (v != null && v.equals(listener)) {
                listeners.remove(i);
                return;
            }
        }
    }

    private void notify(K key, V value, String action) {
        for (WeakReference<HwListener<K, V>> listener : listeners) {
            Objects.requireNonNull(listener.get()).notify(key, value, action);
        }
    }
}
