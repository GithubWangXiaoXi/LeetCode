package com.wangxiaoxi.greedyStrategies;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class code0002_goldCutting {

    /** 问题描述：最小金条切分代价问题
     *  如果一个金条为60，三人分别需要10，20，30，
     *  1）如果60切成10，50，则第一次费用为60，50切成30，20，则费用为50，总共110
     *  2）如果60切分成30，30，则第一次费用为60，30切成10，20，则费用为30，总共90
     *
     *  解题思路：
     *  哈夫曼树问题：先从10，20，30中取出最小的两个树（10，20），合成一个父节点（30），
     *  并在30，30中找两个最小的合成60，则非叶子节点累加起来即为最小的代价（30 + 60 = 90）
     *
     *  哈夫曼树其实就是一个贪心的策略：每次在序列中选最小的2个，则代价为每次最小的2个的和
     *  至于是否合理，则无需证明
     */

     public void getMinCostByGoldCutting(int arr[]) {

         //先将数组依次加入到优先队列中，使其每次弹出队列的最小值
         PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
             @Override
             public int compare(Integer o1, Integer o2) {
                 return o1 - o2;
             }
         });

         for(int i = 0; i < arr.length; i++){
             queue.add(arr[i]);
         }

         int totalCost = 0;
         while(queue.size() > 1){
             int cost = queue.poll() + queue.poll();
             queue.add(cost);
//             System.out.println(cost);

             totalCost += cost;
         }

         System.out.println("最小代价为:" + totalCost);
     }

     @Test
     public void getMinCostByGoldCuttingTest(){
         int arr[] = {10,20,30,15};
         getMinCostByGoldCutting(arr);
     }
}
