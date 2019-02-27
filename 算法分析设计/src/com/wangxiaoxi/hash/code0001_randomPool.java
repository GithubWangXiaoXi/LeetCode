package com.wangxiaoxi.hash;

import org.junit.Test;

import java.util.HashMap;

public class code0001_randomPool {

    /**
     *     设计一个randomPool结构，有insert，delete key值的方法，并且添加时key不重复
     * 并且能等概率地返回一个key值的getRandomKey方法。
     */

    /**
     * 实现思路：
     * key值添加时不重复，用hashSet或者hashmap结构
     * key能通过索引int值（random函数产生一个随机等概率的int值），得到对应位置的key（单射，满射）
     * key在删除时不能影响getRandom等概率获取key值
     *
     * 1，需要两张hashMap，key是map1中的key，是map2中的值，
     *    而map1的值和map2的key则是key在添加或删除过程中对应的size（eg:map1:第999个加入的key的值可以不是999，只要保证map1的value和map2的key能匹配上即可，相当于函数关系）
     * 2，key在删除时，不能直接删除掉某一个key的数据，这样导致在size内取随机值时，找不到对应的key，这时需要将最后一位的key放到删除行的位置（map1，map2），与此同时需要删除最后一条记录，size--
     */

    class RandomPool<K>{

        private HashMap<K,Integer> map1 = new HashMap<>();
        private HashMap<Integer,K> map2 = new HashMap<>();
        int size = 0;

        //加入不重复的key值
        public void insert(K key){

            if(map1.get(key) != null){
                return;
            }

            map1.put(key,size);
            map2.put(size,key);
            size++;
        }

        //在保持getRandom等概率得到key的前提下，进行key的删除操作
        public void delete(K key){

            if(map1.get(key) == null){
                return;
            }

            int deleteIndex = map1.get(key);

            int lastIndex = size - 1;
            K lastKey = map2.get(lastIndex);

            //保证在delete key之后，deleteIndex保持不变
            map2.put(deleteIndex,lastKey);//改map2在deleteIndex上的值
            map2.remove(lastIndex);
            map1.put(lastKey,deleteIndex);//改map1在deleteIndex上的值，保证map1.get能映射到map2的key值上
            map1.remove(key);

            size--;
        }

        public K getRandom(){

            int num = (int) (Math.random()*size); //生成 0~size-1 的值
            return map2.get(num);
        }
    }

    @Test
    public void test1(){

        RandomPool<String> randomPool = new RandomPool<>();

        for(int i = 0; i < 100; i++){
            randomPool.insert("key"+i);
        }

        for(int i = 0; i < 10; i++){
            System.out.println(i + " " + randomPool.getRandom());
        }

    }
}
