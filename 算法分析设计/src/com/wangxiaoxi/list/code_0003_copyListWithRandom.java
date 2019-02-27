package com.wangxiaoxi.list;

import java.util.HashMap;

public class code_0003_copyListWithRandom {

    /**
     * 复制有next，random指针的的节点的list，由于random指针的随机性，不像next指针一直指向下一个节点，这样导致在用next遍历时，
     * random指向的节点还没出现，复制节点的random指针不知道要指向哪一个节点
     * 实现思路：
     * 法1：使用hashmap，记录源节点和复制节点的对应关系，这样可以通过键值对，通过key源节点的地址，找到对应的复制节点的地址
     * 法2：创建源节点和复制节点依次相连的list，这样可以在找到源节点的同时可以找到对应的复制节点
     */

    static class Node{
        public int data;
        public Node next;
        public Node randomPoint;

        public Node(int data){
            this.data = data;
        }
    }

    //通过键值对，通过key源节点，找到对应的复制节点
    public static Node copyListWithRandom1(Node head) {

        if(head == null){
            return null;
        }

        Node copyHead = null;

        //使用hashmap，记录源节点和复制节点的对应关系
        HashMap<Node,Node> nodeRelationMap = new HashMap<>();
        Node p1 = head;
        while(p1 != null){
            nodeRelationMap.put(p1,new Node(p1.data));
            p1 = p1.next;
        }

        //通过hashMap的键值对复制源节点的next,randomPoint信息到复制节点中
        p1 = head;
        Node copyNode = null;
        while(p1 != null){
           copyNode = nodeRelationMap.get(p1); //得到源节点对应的复制节点
           copyNode.next = nodeRelationMap.get(p1.next); //得到源节点next节点对应的复制节点
           copyNode.randomPoint = nodeRelationMap.get(p1.randomPoint); //得到源节点randomPoint节点对应的复制节点
           p1 = p1.next;
        }

        p1 = head;
        copyHead = nodeRelationMap.get(p1);
        return copyHead;
    }

    //   通过自定义的特殊结构：1-1'-2-2'-3-3'
    //   源节点,得到复制节点
    //   源random节点,得到复制的random节点
    //   在拆链表时,源节点起始在第1个位置，复制节点起始在第2个位置，每次走1步，可以将自定义的链表结构拆分成
    //源链表和复制的链表
    public static Node copyListWithRandom2(Node head) {

         if(head == null){
             return null;
         }

         Node copyHead = null;

         //形成自定义的链表结构
         Node p1 = head;
         while(p1 != null){
             Node node = new Node(p1.data);
             node.next = p1.next;
             p1.next = node;
             p1 = p1.next.next;
         }

        System.out.println("自定义的链表结构:");
        showNodeListByNext(head);

        //给复制的节点复制random指针
        Node twoStep = head;
        while(twoStep != null){

            twoStep.next.randomPoint = twoStep.randomPoint != null ? twoStep.randomPoint.next
                                                                   : null;
            twoStep = twoStep.next.next;
        }

        //拆掉自定义的链表结构成源链表和复制链表
        Node origin = head;
        Node copy = head.next;
        if(copyHead == null){
            copyHead = copy;
        }
        //注意链表结构改变后，指针实际跳动的步数
        while(copy.next != null){
            origin.next = copy.next;
            origin = origin.next;  //由于源节点后面已经连上了下一个源节点，所以origin.next为下一个复制节点

            copy.next = origin.next;

            //到达最后的源节点，需要将其next置为null
            if(origin.next.next == null){
                origin.next = null;
            }

            //因为copy节点已经连上了下一个copy节点，所以下一个源节点的位置是copy.next.next
            copy = copy.next;   //复制节点移动
        }

        System.out.println("origin:");
        showNodeListByNext(head);

        System.out.println("copy:");
        showNodeListByNext(copyHead);

        return copyHead;
    }

    public static void main(String[] args) {

        int[] array = generateArray(10);
//        int[] array1 = {9,3};
        Node head = createNodeByArray(array);
        System.out.println("源链表:");
        System.out.print("next指针:");
        showNodeListByNext(head);
        System.out.print("random指针:");
        showNodeListByRandom(head);

        System.out.println("法1——复制后的链表:");
        Node head1 = copyListWithRandom1(head);
        System.out.print("next指针:");
        showNodeListByNext(head1);
        System.out.print("random指针:");
        showNodeListByRandom(head1);

        System.out.println("法2——复制后的链表:");
        Node head2 = copyListWithRandom2(head);
        System.out.print("next指针:");
        showNodeListByNext(head2);
        System.out.print("random指针:");
        showNodeListByRandom(head2);
    }


    //通过数组来创建有random随机指针的节点链表
    public static Node createNodeByArray(int array[]){
        Node head = null;
        Node temp1 = null;

        for(int i = 0; i < array.length; i++){
            if(head == null){
                head = new Node(array[i]);
                temp1 = head;
            }else{
                temp1.next = new Node(array[i]);
                temp1.randomPoint = temp1.next;
                temp1 = temp1.next;
            }
        }
        return head;
    }

    public static void showNodeListByNext(Node head){
        Node p1 = head;
        while(p1 != null){
            System.out.print(p1.data+" ");
            p1 = p1.next;
        }
        System.out.println();
    }

    public static void showNodeListByRandom(Node head){
        Node p1 = head;
        while(p1 != null){
            System.out.print(p1.data+" ");
            p1 = p1.randomPoint;
        }
        System.out.println();
    }


    //随机数组产生器
    public static int[] generateArray(int size){
        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[(int) (Math.random()*temp)];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }
}
