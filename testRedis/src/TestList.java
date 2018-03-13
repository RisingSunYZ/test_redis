import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

/**
 * Created by admin on 2018/3/12.
 */
public class TestList {

    /**
     * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
     一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素)
     * @param args
     */
    public static void main(String[] args) {
        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());

        conn.lpush("list1","yz","wc","xzg"); //像list添加元素
        System.out.println(conn.lrange("list1",0,-1));//遍历元素
        System.out.println(conn.llen("list1"));//获取元素长度
        System.out.println(conn.lindex("list1",1));//获取 下面 1 的元素
        System.out.println(conn.lpop("list1"));//左侧弹出元素
        System.out.println(conn.rpop("list1"));//右侧弹出元素
        conn.lset("list1",0,"hxx");//设置下面0的元素val
        conn.rpushx("list1","yz");//如果 list1 存在 则set值

        conn.linsert("list1", BinaryClient.LIST_POSITION.AFTER,"yz","wc");//在list1 的 yz 后面插入 wc
        conn.lpush("list1","yz"); //头部添加
        conn.lrem("list1",2,"yz"); //移除 数量count(=0 所有 >0 从头部(左侧) <) 从尾部)为 yz的元素
        conn.ltrim("list1",1,-1);//按下面裁剪集合 返回 范围内的 元素

        conn.lpush("list2","cs1","cs2");
        System.out.println(conn.lrange("list1",0,-1));//[hxx, xzg, wc]
        System.out.println(conn.lrange("list2",0,-1));//[cs2, cs1]
        conn.rpoplpush("list1","list2");//集合list1 右侧弹出元素 加入 list2 左侧
        System.out.println(conn.lrange("list1",0,-1));//[hxx, xzg]
        System.out.println(conn.lrange("list2",0,-1));//[wc, cs2, cs1]

        System.out.println(conn.type("list1"));
    }
}
