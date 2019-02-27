package com.wangxiaoxi.list;

import org.junit.Test;

public class code_0005_findFirstIntersectNode {

    /**
     * 寻找单向链表的第一个相交点，思路：
     * 需要考虑单向链表是无环还是有环，相交的情况一共有几种（注意是单向链表一个next指针只能指向一个节点）：
     * 1,无环和无环，如果相交则终点end一定相同
     * 2,有环和无环，一定不存在相交，如果相交，则无环的会变成有环，自身矛盾
     * 3,有环和有环，一共有三种情况，入环节点相同，入环节点不同，没有相同的入环节点
     *
     * 4,方法改进版
     */

    static class Node{
        public int data;
        public Node next;

        public Node(int data){
            this.data = data;
        }
    }

    //判断是不是环
    public static boolean isloop(Node head){

        Boolean flag = false;

        if(head == null){
            return flag;
        }

        Node slowP = head;
        Node fastP = head;
        if(slowP.next == null || slowP.next.next == null || slowP.next.next.next == null){
            return flag;
        }
        //如果快指针追上慢指针，则存在环
        while(slowP != null && fastP != null){
            slowP = slowP.next;
            fastP = fastP.next.next;
            if(slowP == fastP){
                flag = true;
                break;
            }
        }

        return flag;
    }

    //两个链表都无环,判断是否相交，如果相交则返回第一个相交的节点
    public static Node bothIsNotLoop(Node head1,Node head2){

        Node p1 = head1;
        Node p2 = head2;
        int count1 = 0;  //统计链表1的长度
        int count2 = 0;  //统计链表2的长度，如果要计算两链表的第一个相交节点，则长的链表需要多走之间的差值

        while(p1 != null){
            count1++;
            p1 = p1.next;
        }

        while(p2 != null){
            count2++;
            p2 = p2.next;
        }

        //终点相同,则存在相交
        if(p1 == p2){
             p1 = head1;
             p2 = head2;
             int margin = Math.abs(count1 - count2);

             if( count1 > count2 ){
                 while(margin > 0){
                     p1 = p1.next;
                     margin--;
                 }
             }else{
                 while(margin > 0){
                     p2 = p2.next;
                     margin--;
                 }
             }

             while(p1 != p2){
                 p1 = p1.next;
                 p2 = p2.next;
             }

             return p1;

        }else{
            return null;
        }
    }

    //两个链表都是环,判断是否相交，如果相交则返回第一个相交的节点
    public Node bothAreLoop(Node head1,Node head2){
         Node c1 = getEnterLoopNode(head1);  //得到环1的入环节点
         Node c2 = getEnterLoopNode(head2);  //得到环2的入环节点

         //入环节点不同,则两环可能不相交
         if(c1 != c2){
             //如果c1在返回自身之前，能追上c2，则c1或c2是两个环的第一个相交节点
             Node temp = c1.next;
             boolean flag = false;
             while(temp != c1){
                 temp = temp.next;
                 if(temp == c2){
                     flag = true;
                     break;
                 }
             }

             return flag == true ? c1 : null;
         }
         //入环节点相同,则两环一定相交，相交节点转化成两无环链表的相交节点的问题
         else{
             Node p1 = head1;
             Node p2 = head2;
             int count1 = 0;
             int count2 = 0;

             while(p1 != c1){
                 p1 = p1.next;
                 count1++;
             }
             while(p2 != c1){
                 p2 = p2.next;
                 count2++;
             }

             int margin = Math.abs(count1 - count2);

             p1 = head1;
             p2 = head2;

             if( count1 > count2 ){
                 while(margin > 0){
                     p1 = p1.next;
                     margin--;
                 }
             }else{
                 while(margin > 0){
                     p2 = p2.next;
                     margin--;
                 }
             }

             while(p1 != p2){
                 p1 = p1.next;
                 p2 = p2.next;
             }

             return p1;
         }
    }

    //得到入环节点,实现思路:
    //慢指针走1步，快指针走2步
    //当快指针好慢指针重合时，快指针回到head位置，步数改为1
    //如果快指针和满指针再次相遇，则给节点为入环节点
    public Node getEnterLoopNode(Node head){

        //该链表不是环，返回空节点
        if(!isloop(head)){
            return null;
        }

        Node slowP = head;
        Node fastP = head.next;
        while(slowP != fastP){
            slowP = slowP.next;
            fastP = fastP.next.next;
        }

        //快指针到达head时，慢指针需要向前再走一步
        fastP = head;
        slowP = slowP.next;
        while(slowP != fastP){
            slowP = slowP.next;
            fastP = fastP.next;
        }

        return slowP;
    }


    //创建有环的单向链表
    public static Node createLoop(Node head){

        if(head.next == null){
            System.out.println("不能产生环");
            return null;
        }

        Node p1 = head;
        int nodeCount = 1;
        while(p1.next != null){
            p1 = p1.next;
            nodeCount++;
        }

        //如果nodeCount<3，则不存在环
        if(nodeCount < 5){
            System.out.println("不能产生环");
            return null;
        }

        Node p2 = head.next.next;
        p1.next = p2;  //尾部与链表的第3个节点相连，链表长度起码是5，所以上面规定nodeCount < 5不能形成环

        return head;
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


    //随机数组产生器
    public static int[] generateArray(int size){
//        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[size];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }

    @Test
    //测试两个无环链表的第一个相交节点
    public void test1(){

        //未拼接前的长链表
        int[] array = generateArray(10);
        Node list1_head = createNodeByArray(array);

        System.out.println("链表1:");
        showNodeListByNext(list1_head);

        Boolean flag1 = isloop(list1_head);
        System.out.println("单向链表是否是环:"+flag1);

        //未拼接前的短链表
        int[] array1 = generateArray(3);
        Node list2_head = createNodeByArray(array1);

        System.out.println("链表2:");
        showNodeListByNext(list2_head);
//        list2_head = createLoop(list2_head);

        Boolean flag2 = isloop(list2_head);
        System.out.println("单向链表是否是环:"+flag2);

        /**确定两链表的相交点**/
        //将短的链表的尾部拼接到 长链表的随机节点（>短链表.length）上，使短链表变长，但依旧比长链表短
        int count = 4;  //最后得到list1的节点是第五个节点
        Node p1 = list1_head;
        while(count > 0){
            count--;
            p1 = p1.next;
        }

        //得到list2的尾节点
        Node p2 = list2_head;
        while(p2.next != null){
            p2 = p2.next;
        }

        //相交list1与list2，相交点是list1的第五个节点
        p2.next = p1;


        /**判断两条链表的相交点**/
        Node intersectNode = bothIsNotLoop(list1_head,list2_head);
        System.out.println("第一个相交节点的值为:"+p1.data);

        System.out.println("计算得到第一个相交节点的值为:"+intersectNode.data);
    }

    @Test
    //测试2个有环链表的第一个相交节点(situation1):两个环，相交点相同
    public void test2(){

        //链1
        int[] array = generateArray(2);
        Node list1_head = createNodeByArray(array);

        System.out.println("链表1:");
        showNodeListByNext(list1_head);

        Boolean flag1 = isloop(list1_head);
        System.out.println("单向链表是否是环:"+flag1);

        //环2
        int[] array1 = generateArray(10);
        Node list2_head = createNodeByArray(array1);

        System.out.println("链表2:");
        showNodeListByNext(list2_head);
        list2_head = createLoop(list2_head);

        Boolean flag2 = isloop(list2_head);
        System.out.println("单向链表是否是环:"+flag2);

        //将环list_head2的第二个节点作为链1的尾部连接点
        Node p1 = list1_head;
        while(p1.next != null){
            p1 = p1.next;
        }
        Node p2 = list2_head.next;

        p1.next = p2;

        System.out.println("第一个相交节点的值为:"+p2.data);
        Node intersectNode = bothAreLoop(list1_head,list2_head);
        System.out.println("计算出的第一个相交节点的值为:"+intersectNode.data);
    }

    @Test
    //测试2个有环链表的第一个相交节点(situation2):两个环，相交点不同
    public void test3(){

        //链1
        int[] array = generateArray(3);
        Node list1_head = createNodeByArray(array);

        System.out.println("链表1:");
        showNodeListByNext(list1_head);

        Boolean flag1 = isloop(list1_head);
        System.out.println("单向链表1是否是环:"+flag1);

        //环2
        int[] array1 = generateArray(10);
        Node list2_head = createNodeByArray(array1);

        System.out.println("链表2:");
        showNodeListByNext(list2_head);
        list2_head = createLoop(list2_head);

        Boolean flag2 = isloop(list2_head);
        System.out.println("单向链表2是否是环:"+flag2);

        //得到环的入环点,然后在环内找一个节点，作为链表1形成环的入环节点
        Node c1 = getEnterLoopNode(list2_head);
        c1 = c1.next.next.next;  //这样相交的节点为 3 + 3 = 6 (前面的3为入环节点的位置)

        Node p1 = list1_head;
        while(p1.next != null){
            p1 = p1.next;
        }
        p1.next = c1;

        Boolean flag3 = isloop(list1_head);
        System.out.println("单向链表1是否是环:"+flag3);

        System.out.println("第一个相交节点的值为:"+c1.data);
        Node intersectNode = bothAreLoop(list1_head,list2_head);
        System.out.println("计算出的第一个相交节点的值为:"+intersectNode.data);
    }


}
