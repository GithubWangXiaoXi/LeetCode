package com.wangxiaoxi.tree.treeUtils;

import com.wangxiaoxi.tree.code0001_preInPosTraversal;
import org.junit.Test;

import java.util.*;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class GenerateSearchBinaryTree {

    public static class Node extends AbstractNode{

        public Node(int value) {
            super(value);
        }
    }

    //    构建一棵搜索二叉树（搜索二叉树没有出现重复节点）:
    //    实现思路:创建一棵二叉树，然后通过给定的序列，修改原有二叉树中的值，实现中序遍历的序列依次上升
    //使用非递归的方式改值，递归改值没思路
    public void generateSearchBinaryTree(AbstractNode head, Queue<Integer> nodeQueue){

        Stack<AbstractNode> stack = new Stack<>();

        if(head == null){
            return;
        }

        stack.push(head);
        AbstractNode node = stack.pop();

        while (!stack.isEmpty() || node != null) {

            //沿一个方向一直向左走
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            node.value = nodeQueue.poll();  //从最左节点开始，依次修改成序列中的值
//            System.out.print(node.value + "  ");
            node = node.right;
        }
    }

    //得到有序的数组，并保存在队列中
    public  Queue<Integer> getQueueInOrder(int[] array){

        Arrays.sort(array);
        Queue<Integer> queue = new LinkedList<>();

        for(int i = 0; i < array.length; i++){
            queue.offer(array[i]);
        }

        return queue;
    }

    //随机数组产生器
    public int[] generateArray(int size){
//        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[size];
        Set<Integer> set = new HashSet<>();

        for(int i = 0;i<arr.length;i++){

            int num = (int) (Math.random()*30)+1;

            //保证数组无重复元素
            while(set.contains(num)){
                num = (int) (Math.random()*30)+1;
            }

            set.add(num);
            arr[i] = num;
        }

        return arr;
    }

    @Test
    //测试中序遍历
    public void test1(){

        int num = 10;

        AbstractNode head = new code0001_preInPosTraversal().generateTree(num);
        printTree(head);


        System.out.println("InOrder1:");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();

        System.out.println("generateSearchBinaryTree:");
        int array[] = generateArray(num);
        Queue<Integer> queue = getQueueInOrder(array);
        generateSearchBinaryTree(head,queue);
        System.out.println();

        printTree(head);
    }
}
