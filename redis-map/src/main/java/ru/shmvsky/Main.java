package ru.shmvsky;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class Main {
    public static void main(String[] args) {

        try (RedisMap redisMap = new RedisMap(new Jedis()))
        {
            redisMap.put("key", "value2");
            System.out.println(redisMap.get("key"));
        }


    }
}