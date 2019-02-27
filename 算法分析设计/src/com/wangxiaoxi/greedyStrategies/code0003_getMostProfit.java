package com.wangxiaoxi.greedyStrategies;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class code0003_getMostProfit {

    /**
     * 问题描述：求启动k个项目后最终得到的最大收益
     * 每个项目有profit，cost两个属性，有一笔启动资金M，要求做了k个项目，项目总个数大于k个，并且多个项目不能同时进行，求最终的最大收益
     *
     * 解题思路：
     * 1，有一笔启动资金，则该启动资金需要首先开启cost < M的，并且其中profit最大的项目，做完一个项目后得到利润，M增加
     * 2，先对项目进行cost降序排序，选出小于M的项目，并对其再次进行profit的升序排序
     */

     class Node{
         int cost;
         int profit;

        public Node(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
     }

     public int getMostProfit(Node nodeArr[], int money, int projectNum){

         PriorityQueue<Node> costQueue = new PriorityQueue<>(new Comparator<Node>() {
             @Override
             public int compare(Node o1, Node o2) {
                 return o1.cost - o2.cost;
             }
         });

         //按项目的利益升序排序
         PriorityQueue<Node> profitQueue = new PriorityQueue<>(new Comparator<Node>() {
             @Override
             public int compare(Node o1, Node o2) {
                 return -( o1.profit - o2.profit );
             }
         });

         for(int i = 0; i < nodeArr.length; i++){
             costQueue.add(nodeArr[i]);
         }

         while(projectNum > 0){

             //先将符合要求的项目移动到大根堆中，进行profit排序
             while(costQueue.peek().cost < money){
                 profitQueue.add(costQueue.poll());
             }

             //如果大根堆中没有项目,则无项目可做了
             if(profitQueue.size() == 0){
                 return money;
             }

             Node node = profitQueue.poll();
             money += node.profit;

             projectNum--;
         }

         return money;
     }

     @Test
     public void getMostProfitTest(){

         int cost[] = {5,10,6,20};
         int profit[] = {3,2,4,9};
         Node nodeArr[] = new Node[cost.length];

         for(int i = 0; i < cost.length; i++){
             nodeArr[i] = new Node(cost[i],profit[i]);
         }

         int money = getMostProfit(nodeArr,7,4);
         System.out.println("maxProfit:" + money);
     }
}
