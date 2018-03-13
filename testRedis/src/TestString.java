import redis.clients.jedis.Jedis;

/**
 * Created by admin on 2018/3/12.
 */
public class TestString {
    /**
     * Redis 字符串数据类型的相关命令用于管理 redis 字符串值
     * @param args
     */
    public  static  void  main(String [] args) {
        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());//pang 代表链接成功

        conn.set("test","123");//set key test val 123
        System.out.println(conn.get("test"));//get key
        System.out.println(conn.exists("test"));// key是否存在
        System.out.println(conn.strlen("test"));//返回key的长度
        System.out.println(conn.getrange("test",0,-1));//返回截取字符串长度 0 -1 截取全部
        System.out.println(conn.getrange("test",1,2));

        System.out.println(conn.append("test","appendStr"));//追加
        System.out.println(conn.get("test"));

        conn.rename("test","test_new");//重命名
        System.out.println(conn.exists("test"));


        conn.mset("key1","val1","key2","val2");//批量插入
        System.out.println(conn.mget("key1","key2"));//批量取出
        System.out.println(conn.del("key1"));//删除
        System.out.println(conn.exists("key1"));

        System.out.println(conn.getSet("key2","2"));//取出旧值 并set新值
        System.out.println(conn.incr("key2")); //自增1 要求数值类型
        System.out.println(conn.incrBy("key2",5));//自增5 要求数值类型
        System.out.println(conn.decr("key2"));//自减1 要求数值类型
        System.out.println(conn.decrBy("key2",5));
        System.out.println(conn.incrByFloat("key2",1.1));//增加浮点类型

        System.out.println(conn.setnx("key2","existVal"));//返回0 只有在key不存在的时候才设置
        System.out.println(conn.get("key2"));// 3.1

        System.out.println(conn.msetnx("key2","exists1","key3","exists2"));//只有key都不存在的时候才设置
        System.out.println(conn.mget("key2","key3"));// null

        conn.setex("key4",3,"3 seconds is no Val");//设置key 3 秒后失效
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(conn.get("key4"));// 3 seconds is no Val

        conn.psetex("key5",2000,"2000 milliseconds is no Val ");//设置秒数单位为毫秒
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(conn.get("key5"));//null

        conn.set("key6","123456789");
        conn.setrange("key6",3,"abcdefg"); //从第三位开始 将值覆盖 下标从0 开始
        System.out.println(conn.get("key6"));//123abcdefg

        System.out.println(conn.type("key6"));//返回数据类型  string

    }


}
