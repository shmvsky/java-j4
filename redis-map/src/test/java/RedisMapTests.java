import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import redis.clients.jedis.Jedis;
import ru.shmvsky.RedisMap;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisMapTests {

    private Jedis jedis;
    private RedisMap redisMap;

    @BeforeEach
    void setUp() {
        jedis = Mockito.mock(Jedis.class);
        Set<String> keys = new HashSet<>();
        keys.add("key1");
        keys.add("key2");

        Mockito.when(jedis.keys("*")).thenReturn(keys);
        Mockito.when(jedis.type("key1")).thenReturn("string");
        Mockito.when(jedis.type("key2")).thenReturn("string");
        Mockito.when(jedis.get("key1")).thenReturn("value1");
        Mockito.when(jedis.get("key2")).thenReturn("value2");

        redisMap = new RedisMap(jedis);
    }

    @AfterEach
    void tearDown() {
        redisMap.close();
        Mockito.verify(jedis).close();
    }

    @Test
    void testInitialization() {
        Set<Map.Entry<String, String>> entries = redisMap.entrySet();
        Assertions.assertEquals(2, entries.size());
        Assertions.assertTrue(entries.contains(new AbstractMap.SimpleEntry<>("key1", "value1")));
        Assertions.assertTrue(entries.contains(new AbstractMap.SimpleEntry<>("key2", "value2")));
    }

    @Test
    void testPutNewKey() {
        String oldValue = redisMap.put("key3", "value3");
        Assertions.assertNull(oldValue);

        Set<Map.Entry<String, String>> entries = redisMap.entrySet();
        Assertions.assertEquals(3, entries.size());
        Assertions.assertTrue(entries.contains(new AbstractMap.SimpleEntry<>("key3", "value3")));

        Mockito.verify(jedis).set("key3", "value3");
    }

    @Test
    void testPutExistingKey() {
        String oldValue = redisMap.put("key1", "newValue1");
        Assertions.assertEquals("value1", oldValue);

        Set<Map.Entry<String, String>> entries = redisMap.entrySet();
        Assertions.assertEquals(2, entries.size());
        Assertions.assertEquals(redisMap.get("key1"), "newValue1");

        Mockito.verify(jedis).set("key1", "newValue1");
    }

}
