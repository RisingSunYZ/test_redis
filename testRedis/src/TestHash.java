import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/3/12.
 */
public class TestHash {
    /**
     * Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。
        Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）
     * @param args
     */
    public static void main(String[] args) {

        Jedis conn = new Jedis("localhost");
        conn.hset("student","name","yz");
        System.out.println(conn.hget("student","name"));

        Map<String,String> studentFields = new HashMap<String,String>();
        studentFields.put("score","56");
        studentFields.put("addr","gaoxin");
        conn.hmset("student",studentFields);//批量添加
        System.out.println(conn.hgetAll("student"));//获取键值
        System.out.println(conn.hmget("student","score","name"));//批量获取key中field对应的val

        conn.hincrByFloat("student","score",1.2);// 同 String
        System.out.println(conn.hget("student","score"));

        System.out.println(conn.hkeys("student"));//获取所有的key
        System.out.println(conn.hvals("student"));//获取所有的val

        System.out.println(conn.hlen("student"));//获取长度
        System.out.println(conn.hexists("student","name"));//判断是否存在

        conn.hdel("student","addr");//删除一个field
        System.out.println(conn.hexists("student","addr"));

        conn.hsetnx("student","addr","hefei");//不存在 即设置
        System.out.println(conn.hget("student","addr"));

        System.out.println(conn.type("student"));//hash
    }
}
