package com.wangxiaoxi.tree;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0007_isCompleteBT {

    /**判断是否是完全二叉树*/

    // 1，如果该节点只有右节点，没有左节点，不是完全二叉树
    // 2，如果该节点只有左节点（或者左右节点同时存在，或同时不存在），则该层以及下一层的所有节点都是叶子节点
    // 注意需要逐层判断完全二叉树（如果上面出现false，则整棵树为false）
    public Boolean isCompleteBT(AbstractNode head){

        Boolean flag = true;
        Boolean isleaf = false;

        if(head == null){
            return flag;
        }

        Queue<AbstractNode> queue = new LinkedList<>();
        queue.offer(head);

        while(!queue.isEmpty()){

            AbstractNode node = queue.poll();

            if(node.left != null){
                queue.offer(node.left);
            }

            if(node.right != null){
                queue.offer(node.right);
            }

            // 如果该节点只有右节点，没有左节点，不是完全二叉树
            if(node.left == null && node.right != null){
                return false;
            }

            // 如果该节点只有左节点，则该层以及下一层的所有节点都是叶子节点
            if(isleaf && (node.left != null || node.right != null)){
                return false;
            }

            //  l null,r null;
            //  l null,r notNull; (该情况被上面过滤掉了)
            //  r null,l notNull;
            //  开启node节点以后的所有节点都是叶子节点的状态
            if(node.left == null || node.right == null){
                isleaf = true;
            }
        }

        return true;
    }

    @Test
    public void test1(){

        AbstractNode head = new code0001_preInPosTraversal().generateTree(10);
        printTree(head);

        System.out.println("inOrder1:");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();

        Boolean flag = isCompleteBT(head);
        System.out.println("是否是完全二叉树:" + flag);

        //去掉完全二叉树的最左节点,变成非完全二叉树
        AbstractNode node = head;
        AbstractNode temp = node.left;
        while(temp.left != null){

            node = temp;
            temp = temp.left;
        }

//        node.left = null;
        node.right = null;
        printTree(head);

        System.out.println("inOrder2:");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();

        Boolean flag1 = isCompleteBT(head);
        System.out.println("是否是完全二叉树:" + flag1);
    }
}
