package test;

import redis.clients.jedis.Jedis;

public class RedisPush {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("106.15.195.195");
//        for(int i = 0; i < 49900; i++)
//            jedis.lpush("4204444444", "{1:2,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3" +
//                    ",2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3,2:3}");
//        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }
}
