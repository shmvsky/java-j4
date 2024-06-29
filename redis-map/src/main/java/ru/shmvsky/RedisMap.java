package ru.shmvsky;

import redis.clients.jedis.Jedis;

import java.io.Closeable;
import java.util.*;

public class RedisMap extends AbstractMap<String, String> implements Closeable {

    private final Set<Entry<String, String>> entries;

    private final Jedis jedis;

    public RedisMap(Jedis jedis) {
        Set<String> keys = jedis.keys("*");

        HashSet<Entry<String, String>> entries = new HashSet<>();

        for (String key : keys) {
            if (jedis.type(key).equals("string")) {
                entries.add(new SimpleEntry<>(key, jedis.get(key)));
            }
        }

        this.jedis = jedis;
        this.entries = entries;
    }

    @Override
    public String put(String key, String value) {
        jedis.set(key, value);
        for (Entry<String, String> entry : entries) {
            if (entry.getKey().equals(key)) {
                String oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        entries.add(new SimpleEntry<>(key, value));
        return null;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return entries;
    }

    @Override
    public void close() {
        jedis.close();
    }
}
