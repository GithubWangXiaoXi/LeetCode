package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import com.wangxiaoxi.tree.treeUtils.GenerateSearchBinaryTree;
import org.junit.Test;

import java.util.Queue;
import java.util.Stack;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0006_isSearchBinaryTree {


    /**判断是否是搜索二叉树*/
    public Boolean isSearchBinaryTree(AbstractNode head){

        Stack<AbstractNode> stack = new Stack<>();
        Boolean flag = true;
        int min = Integer.MIN_VALUE;

        if(head == null){
            return flag;
        }

        stack.push(head);
        AbstractNode node = stack.pop();

        //搜索二叉树:中序遍历依次递增，这样只需保证中序遍历的序列是否递增即可!!!，不用考虑走哪个节点比较，宏观一点
        while (!stack.isEmpty() || node != null) {

            //沿一个方向一直向左走
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if(min < node.value){
                min = node.value;
            }else{
                flag = false;
                break;
            }

            System.out.print(node.value + "  ");
            node = node.right;
        }

        return flag;
    }

    @Test
    public void test1(){

        AbstractNode head = new code0001_preInPosTraversal().generateTree(10);
        printTree(head);

        System.out.println("inOrder1:");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();

        Boolean flag = isSearchBinaryTree(head);
        System.out.println("是否是搜索二叉树:" + flag);


        int[] array = new GenerateSearchBinaryTree().generateArray(10);
        Queue<Integer> queue = new GenerateSearchBinaryTree().getQueueInOrder(array);
        new GenerateSearchBinaryTree().generateSearchBinaryTree(head,queue);
        printTree(head);

        System.out.println("inOrder2:");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();

        Boolean flag1 = isSearchBinaryTree(head);
        System.out.println("是否是搜索二叉树:" + flag1);
    }
}
