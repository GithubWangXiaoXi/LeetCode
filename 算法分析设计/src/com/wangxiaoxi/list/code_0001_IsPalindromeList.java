package com.wangxiaoxi.list;

import java.util.Arrays;
import java.util.Stack;

public class code_0001_IsPalindromeList {

    /**
     *   判断链表结构是否是回文结构，
     *     法1：先将链表中的数全部压到栈中，再弹出栈顶元素和链表依次匹配
     *     法2：使用快慢指针，慢指针到中点的位置，将慢指针后面的数都压到栈中，再和链表匹配
     *     法3：使用快慢指针，慢指针到中点的位置，先获取中点的next节点A，并将中点节点的next指向空，
     *   A先获取next节点B，A将箭头反向，指向中点，B先获取C，再next反向指向A
     */

    static class Node{
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }
    }

    //法1:先将链表中的数全部压到栈中，再弹出栈顶元素和链表依次匹配
    public static boolean isPalindromeList1(Node head){
        Stack<Integer> stack = new Stack<Integer>();

        Node p1 = head;

        //先将链表中的数全部压到栈中
        while(p1 != null){
            int num = p1.data;
            stack.push(num);
            p1 = p1.next;
        }

        p1 = head;
        boolean flag = true;
        //再弹出栈顶元素和链表依次匹配
        System.out.print("栈顶元素:");
        while(p1 != null){
            int listNum = p1.data;
            int stackTopNum = stack.pop();
            System.out.print(stackTopNum+" ");
            if(listNum != stackTopNum){
                flag = false;
                break;
            }
            p1 = p1.next;
        }

        return flag;

    }

    //法2:使用快慢指针，慢指针到中点的位置，将慢指针后面的数都压到栈中，再和链表匹配
    public static boolean isPalindromeList2(Node head){
        Node slowP = head;  //慢指针走1步
        Node fastP = head;  //慢指针走2步

        //如果链表length = 2 或等于 1,判断是否为回文结构
        if(slowP.next == null){
            return true;
        }
        else if(fastP.next.next == null){
            int firstNum = slowP.data;
            int secondNum = slowP.next.data;
            if(firstNum == secondNum){
                return true;
            }else{
                return false;
            }
        }
        //如果链表length > 2
        else{
            Stack<Integer> stack = new Stack<>();

            slowP = slowP.next;
            fastP = fastP.next.next;
            Node medium;
            while(fastP.next != null && fastP.next.next != null){
                slowP = slowP.next;
                fastP = fastP.next.next;
            }
            medium = slowP;

            //将慢指针后面的数都压到栈中，再和链表匹配
            medium = medium.next;
            while(medium != null){
                int num = medium.data;
                stack.push(num);
                medium = medium.next;
            }

            //再和链表匹配
            Node begin = head;
            boolean flag = true;
            System.out.print("栈顶元素:");
            while(begin != slowP.next && !stack.isEmpty()){
                System.out.print(stack.peek()+" ");
                if(begin.data != stack.pop()){
                    flag = false;
                }
                begin = begin.next;
            }
            return flag;
        }
    }


    //法3:辅助空间为O(1)
    //       使用快慢指针，慢指针到中点的位置，先获取中点的next节点A，并将中点节点的next指向空，
    //    A先获取next节点B，A将箭头反向，指向中点，B先获取C，再next反向指向A
    public static boolean isPalindromeList3(Node head){
        Node slowP = head;  //慢指针走1步
        Node fastP = head;  //慢指针走2步

        //如果链表length = 2 或等于 1,判断是否为回文结构
        if(slowP.next == null){
            return true;
        }
        else if(fastP.next.next == null){
            int firstNum = slowP.data;
            int secondNum = slowP.next.data;
            if(firstNum == secondNum){
                return true;
            }else{
                return false;
            }
        }
        //如果链表length > 2
        else {
            slowP = slowP.next;
            fastP = fastP.next.next;
            Node medium;
            while (fastP.next != null && fastP.next.next != null) {
                slowP = slowP.next;
                fastP = fastP.next.next;
            }

            //先获取中点的next节点A，并将中点节点的next指向空，改变了链表的结构，在判断结束后，要把它重置回来
            Node nextNode = slowP.next; //保存下一个节点的信息
            Node frontNode = slowP;  //保存前一个节点的信息
            Node finalNode = null; //保存最右端的节点信息
            Node temp = null;

            slowP.next = null;
            while(nextNode != null){
                temp = nextNode.next;
                nextNode.next = frontNode;  //nextNode指向前面的节点
                frontNode = nextNode;    //frontNode节点向后移动
                nextNode = temp;  //nextNode节点向后移动
            }

            finalNode = frontNode;
            Node beginNode = head;
            boolean flag = true;

            //中点的位置next指向null,当左右有一个走到中点的位置，则结束
            while(frontNode.next != null && beginNode.next != null){
                int latter = frontNode.data;
                int front = beginNode.data;
                if(latter != front){
                    flag = false;
                    break;
                }
                frontNode = frontNode.next;
                beginNode = beginNode.next;
            }


            Node nextNode1 = finalNode.next;
            Node nextNode2 = nextNode1.next;
            Node temp1 = finalNode;

            //将链表的后半部分重置,注意检查链表连接，中止条件是否正确，否则会出现死循环
            while(nextNode1 != null){
//                nextNode1 = finalNode.next;  //finalNode.next是向右方向的，nextNode1是向左方向的，这样会导致形成环
                temp1.next = null;   //不用finalNode.next=null，finalNode会动的
                nextNode2 = nextNode1.next;
                nextNode1.next = finalNode;

                finalNode = nextNode1;
                nextNode1 = nextNode2;
            }

            //打印链表是否恢复原状
            System.out.println();
            System.out.println("恢复原状");
            showNodeList(head);

            return flag;
        }
    }

    public static void main(String[] args) {

        int[] array = generatePalindromeArray(8);
        Node head = createNodeByArray(array);
        showNodeList(head);

        boolean flag1 = isPalindromeList1(head);

        System.out.println();
        System.out.print("法1:是否是回文结构:");
        System.out.println(flag1);

        boolean flag2 = isPalindromeList2(head);
        System.out.println();
        System.out.print("法2:是否是回文结构:");
        System.out.println(flag2);

        boolean flag3 = isPalindromeList3(head);
        System.out.println();
        System.out.print("法3:是否是回文结构:");
        System.out.println(flag3);
    }

    //通过数组来给节点赋值
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

    public static void showNodeList(Node head){
        Node p1 = head;
        while(p1 != null){
            System.out.print(p1.data+" ");
            p1 = p1.next;
        }
        System.out.println();
    }

    //生成回文结构的数组
    public static int[] generatePalindromeArray(int size){

        int medium;
        int[] array = new int[size];
        //如果size是单数,eg: 1 2 1 medium: 2; index = 1
        if(size % 2 == 1){
            medium = size / 2 + 1;
        }
        //如果size是偶数
        else{
            medium = size / 2;
        }

        for(int i = 0; i < medium; i++){
            int num = (int) (Math.random()*10+1);
            array[i] = num;
            array[array.length - 1 - i] = num;
        }

        return array;
    }

}
