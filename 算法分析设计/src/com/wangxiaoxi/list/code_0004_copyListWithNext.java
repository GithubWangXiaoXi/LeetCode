package com.wangxiaoxi.list;

import java.util.HashMap;

public class code_0004_copyListWithNext {

    /**
     *  复制只有next指针的节点链表
     */

    static class Node{
        public int data;
        public Node next;

        public Node(int data){
            this.data = data;
        }
    }

    public static Node copyList(Node head){
        Node copyHead = null;

        Node p1 = head;
        Node frontP = null;
        while(p1 != null){
            Node node = new Node(p1.data);

            if(copyHead == null){
                copyHead = node;
            }

            //frontP为当前节点的前一个节点
            if(frontP != null){
                frontP.next = node;
            }
            p1 = p1.next;
            frontP = node;
        }

        return copyHead;
    }

    public static void main(String[] args) {

        int[] array = generateArray(10);
        Node head = createNodeByArray(array);
        System.out.println("源链表:");
        showNodeListByNext(head);

        System.out.println("复制后的链表:");
        Node copyHead = copyList(head);
        showNodeListByNext(copyHead);
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
        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[(int) (Math.random()*temp)];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }
}
