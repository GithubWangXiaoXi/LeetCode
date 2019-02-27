package com.wangxiaoxi.set;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.util.*;

public class code0001_unionfindSet {

    /**
     * 问题：如何判断两个节点是否在同一个集合中，如果不在，对两个集合进行合并
     *             这个问题需要继续细化：如果一开始是各个离散相互独立的点，则每个节点都是一个set或list，
     *         再来完全遍历两个set，判断两个节点是否在同一个set。时间复杂度比自定义的unionSet大，
     *         而unionSet每次查询只需向上找即可，而且每次查找完后，会优化路径，节点直接连向代表结点
     *
     *     如果用list或普通的set（hashset无序，treeset有序）：
     *     1，判断两节点是否在同一个集合，则需要遍历其中一个节点的（小的）集合（用list/set），判断另一个节点是否在其中
     *     2，合并两个集合（两个list连在一起，但是在查询时需要遍历，代价较高/hashSet合并后，在查询时也需要遍历）
     *     TreeSet 依靠的是Comparable 来区分重复数据
     *     HashSet 依靠的是hashCode()、equals()来区分重复数据
     *
     *     所以需要用并查集来快速查询不同节点（一开始只有一个节点是一个集合，然后才能从个人发展到部落）是否在同一个集合中，
     * 以及将不同的集合合并。
     *     1，通过链表初始化集合中的数据时，每个节点都是一个集合。
     *     2，判断两个节点是否在同一个集合中:判断两个节点的代表节点是否相同。
     *     3，对两个集合进行合并：小的集合的代表节点连在大的集合的代表节点下面。
     *     4，路径压缩：在每一次查询节点X并找到对应的代表节点A后，会将X节点以上的节点都直接连向A。（判断和合并的操作都会执行路径压缩）
     *
     * 并查集的应用：kruskal的最小生成树
     */

    /**
     *     在一些应用问题中，需要将n个不同的元素划分成一组不相交的集合。开始时每个元素自成一个单元素集合，然后按照一定规律将归于同一组元素的集合合并，
     * 在此过程中需要反复查询某个元素归属于哪个集合的运算，适合于描述这类问题的抽象数据类型称为并查集（union-find set）。
     */

     //并查集接收的节点类型
     class Node{

        int value;

        public Node(int value){
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

     //并查集一共有两个功能: 并(合并两个集合), 查(查某个元素是否在集合中，两个元素是否在同一个集合中)
     class UnionFindSet<K>{

         HashMap<K,K> childParentMap;//key:childNode, value:parentNode  //记录child和parent的关系,递归可得到代表节点（使各个独立的节点建立关系）
         HashMap<K,Integer> nodeSizeMap;//key:node, value:size   //主要记录代表节点下一共有多少个节点

         //用list一次性传入,list中的每个节点相互独立，各自是一个集合
         public UnionFindSet(List<K> nodeList){
             childParentMap = new HashMap<>();
             nodeSizeMap = new HashMap<>();
             for(K node : nodeList){
                 childParentMap.put(node,node);  //初始化时各节点自己的父节点为自己
                 nodeSizeMap.put(node,1);
             }
         }

         //找到某节点的代表节点,并路径压缩（沿路的节点都指向代表节点）（递归实现）,对外不可见
         private K findHead1(K node){

             K parent = childParentMap.get(node);

             if(node != parent){
                 parent = findHead1(parent);  //递归执行到最后，返回代表节点
                 childParentMap.put(node,parent);
             }

             return parent;
         }

         //非递归实现（用栈来记录沿路的节点，并改变节点的指向路径（应该用队列也行））
         private K findHead2(K node){

             Stack<K> stack = new Stack<>();

             K parent = childParentMap.get(node);
             while(node != parent){
                 stack.push(node);
                 node = parent;
                 parent = childParentMap.get(node);
             }

             //压缩路径（把链压平）
             while(!stack.isEmpty()){
                 node = stack.pop();
                 childParentMap.put(node,parent);
             }

             return parent;
         }

         //判断两个节点是否在同一个集合
         public boolean isSameSet(K node1, K node2){

             System.out.println(findHead1(node1));
             System.out.println(findHead2(node2));
             return findHead1(node1) == findHead2(node2);
         }

         //如果两个节点不在同一个集合，则两个集合合并:小的代表节点连在大的代表结点下
         public void union(K node1, K node2){

             if(!isSameSet(node1,node2)){

                 K node1_head = findHead1(node1);
                 K node2_head = findHead1(node2);

                 int size1 = nodeSizeMap.get(node1_head);
                 int size2 = nodeSizeMap.get(node2_head);

                 if(size1 <= size2){
                      childParentMap.put(node1_head, node2_head);
                      nodeSizeMap.put(node2_head, size1 + size2);
                 }else{
                     childParentMap.put(node2_head, node1_head);
                     nodeSizeMap.put(node1_head, size1 + size2);
                 }
             }
         }
     }

     @Test
     public void unionSetTest(){

         List<Node> nodeList = new LinkedList<>();
         UnionFindSet<Node> unionSet;

         for(int i = 0; i < 10; i++){
             Node node = new Node(i);
             nodeList.add(node);
         }

         //unionSet将链表中的元素拆成每个每个集合
         unionSet = new UnionFindSet<>(nodeList);

         Node node2 = nodeList.get(2);
         Node node5 = nodeList.get(5);

         Boolean flag = unionSet.isSameSet(node2,node5);
         System.out.println("node2和node5是否在同一个集合中:" + flag);

         unionSet.union(node2,node5);

         Boolean flag1 = unionSet.isSameSet(node2,node5);
         System.out.println("node2和node5是否在同一个集合中:" + flag1);
     }

}
