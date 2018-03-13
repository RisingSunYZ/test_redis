import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2018/3/13.
 */
public class TestZSet {

    /**
     * Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
     不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
     有序集合的成员是唯一的,但分数(score)却可以重复。
     集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     * @param args
     */
    public static void main(String[] args) {

        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());

        Map<String,Double> members = new HashMap<String,Double>();
        members.put("yz",12.0);
        members.put("zwl",67.5);
        members.put("xzg",34.0);
        conn.zadd("zset1",members);//添加元素

        System.out.println(conn.zcard("zset1"));//获取集合元素个数
        System.out.println(conn.zrange("zset1",0,-1));//按照下标起始终止遍历元素
        System.out.println(conn.zrangeByScore("zset1",30,60));//按照分数起始终止遍历元素
        Set<Tuple> res0 = conn.zrangeByScoreWithScores("zset1",30,60);//按照分数起始终止遍历元素 返回 field score
        for(Tuple temp:res0){
            System.out.println(temp.getElement()+" : "+temp.getScore());
        }
        System.out.println(conn.zrevrange("zset1",0,-1));//按照下标起始终止倒序遍历元素
        System.out.println(conn.zrevrangeByScore("zset1",60,0));//按照分数起始终止倒序遍历元素

        System.out.println(conn.zscore("zset1","yz"));//获取元素score值
        System.out.println(conn.zcount("zset1",0,60));//获取元素分数区间的元素

        System.out.println(conn.zrank("zset1","yz"));//获取元素下标
        System.out.println(conn.zrevrank("zset1","yz"));//倒序获取元素下标
        System.out.println(conn.zrem("zset1","yz","zwl"));//删除元素
        System.out.println(conn.zremrangeByRank("zset3",0,-1));//删除元素通过下标范围
        System.out.println(conn.zremrangeByScore("zset4",0,10));//删除元素通过分数范围
        System.out.println(conn.zrange("zset1",0,-1));

        Map<String,Double> members2 = new HashMap<String,Double>();
        members2.put("yz",36.1);
        members2.put("zwl",12.5);
        members2.put("xzg",24.0);
        conn.zadd("zset2",members2);//添加元素
        System.out.println( conn.zincrby("zset1",1,"yz"));//增加指定分数
        Set<Tuple> rs = conn.zrangeWithScores("zset1",0,-1);
        for(Tuple temp:rs){
            System.out.println(temp.getElement()+" : "+temp.getScore());
        }
        System.out.println("===================================");
        Set<Tuple> rs2 = conn.zrangeWithScores("zset2",0,-1);
        for(Tuple temp:rs2){
            System.out.println(temp.getElement()+" : "+temp.getScore());
        }
        System.out.println("===================================");

        conn.zinterstore("zset3","zset1","zset2");
        Set<Tuple> rs3 = conn.zrangeWithScores("zset3",0,-1);
        for(Tuple temp:rs3){
            System.out.println(temp.getElement()+" : "+temp.getScore());
        }

        /**
         * ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
         计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。
         默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。

         WEIGHTS

         使用 WEIGHTS 选项，你可以为 每个 给定有序集 分别 指定一个乘法因子(multiplication factor)，
         每个给定有序集的所有成员的 score 值在传递给聚合函数(aggregation function)之前都要先乘以该有序集的因子。
         如果没有指定 WEIGHTS 选项，乘法因子默认设置为 1 。

         AGGREGATE

         使用 AGGREGATE 选项，你可以指定并集的结果集的聚合方式。
         默认使用的参数 SUM ，可以将所有集合中某个成员的 score 值之 和 作为结果集中该成员的 score 值；
         使用参数 MIN ，可以将所有集合中某个成员的 最小 score 值作为结果集中该成员的 score 值；
         而参数 MAX 则是将所有集合中某个成员的 最大 score 值作为结果集中该成员的 score 值。
         */
        conn.zunionstore("zset4","zset1","zset2");
        Set<Tuple> rs4 = conn.zrangeWithScores("zset4",0,-1);
        for(Tuple temp:rs4){
            System.out.println(temp.getElement()+" : "+temp.getScore());
        }

        System.out.println(conn.type("zset1"));//zset

    }
}
