import redis.clients.jedis.Jedis;

/**
 * Created by admin on 2018/3/13.
 */
public class TestSet {

    /**
     * Redis 的 Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
     Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
     集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     * @param args
     */
    public static void main(String[] args) {
        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());

        conn.sadd("set1","yz","zwl","xzg","hxx","xzg"); //像集合添加元素
        System.out.println(conn.smembers("set1"));//遍历所有元素
        System.out.println(conn.scard("set1"));//获取集合元素个数
        System.out.println(conn.sismember("set1","hxx"));//判断是否是集合元素
        System.out.println(conn.spop("set1"));//随机弹出一个元素
        System.out.println(conn.srandmember("set1",2));//随机返回 2 个元素 并不会从集合中移除
        System.out.println(conn.srem("set1","yz","zwl"));//移除元素

        conn.sadd("set2","hxx","yz","zwl","xzg");
        System.out.println(conn.sinter("set1","set2"));//求两个集合的交集
        System.out.println(conn.sinterstore("set3","set1","set2"));//求两个集合的交集 并存放到set3
        System.out.println(conn.smembers("set3"));

        System.out.println(conn.sdiff("set1","set2"));//求两个集合的差集 同时也存在  conn.sdiffstore()
        System.out.println(conn.sunion("set1","set2"));//求两个集合的并集 同时也存在  conn.sunionstore()

        System.out.println(conn.smove("set2","set1","yz"));//将yz 从 set2 移动dao set1 同时set2 中yz 移除
        System.out.println(conn.type("set1"));
    }
}
