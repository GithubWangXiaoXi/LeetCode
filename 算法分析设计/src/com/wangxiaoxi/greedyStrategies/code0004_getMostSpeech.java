package com.wangxiaoxi.greedyStrategies;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class code0004_getMostSpeech {

    /**
     * 问题描述:所有的宣讲都在一个会议室上进行，每个宣讲有起始时间和结束时间，求会议室开启的最多宣讲场次
     *
     * 解决思路：
     * 1）贪心1：选时间最早发生的。 反例：如果最早时间的duration过长，其他宣讲无法进行
     *
     * 2）贪心2：选duration最短的。 反例：如果A的结束时间和C的开始时间在B的duration之间，这样A,B没法进行
     *
     * 3）贪心3：选结束时间最早的。至于其正确性，无需去证明。
     */

       class Node{
          int start;
          int end;

          public Node(int start, int end) {
              this.start = start;
              this.end = end;
          }
       }


      public int getMostSpeech(Node nodeArr[]){

          PriorityQueue<Node> endQueue = new PriorityQueue<>(new Comparator<Node>() {
              @Override
              public int compare(Node o1, Node o2) {
                  return o1.end - o2.end;
              }
          });

          for(int i = 0; i < nodeArr.length; i++){
              endQueue.add(nodeArr[i]);
          }

          int cur = 0;
          int num = 0;
          while(!endQueue.isEmpty()){

              if(cur < endQueue.peek().start){
                  cur = endQueue.poll().end;
                  num++;
              }
          }

          return num;
      }

//      @Test
//      public void getMostSpeechTest(){
//           int start[] = {}
//      }
}
