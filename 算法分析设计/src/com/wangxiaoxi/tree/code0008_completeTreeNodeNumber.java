package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import org.junit.Test;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0008_completeTreeNodeNumber {

    /**计算完全二叉树的节点个数，要求时间复杂度小于O(N),及不用遍历所有节点就能知道完全二叉树的节点个数*/
    //  满二叉树的层数为n，则节点总数为2^n - 1
    //  先判断右子树到最下层有没有最左节点
    //  1，如果有，则左子树为满二叉树
    //  2，如果没有，则右子树为满二叉树

    public int nodeNum(AbstractNode head){
        if(head == null){
            return 0;
        }

        int level = getLevel(head);
        int num = getCompleteTreeNodeNumber(head,level);
        return num;
    }

    public int getCompleteTreeNodeNumber(AbstractNode head,int level){

        if(level == 0){
            return 0;
        }

        //如果右子树到最下层有最左节点，则左子树为满二叉树，高度为level-1(右子树 == 树的高度-1)，此时递归右子树
        //考虑1个节点的时候，高度为1，1 - 1 = getLevel(null) = 0，计算得到num = 1。所以1个节点求num命中第一个条件
        if((level - 1) == (getLevel(head.right))){
            //如果每棵子树都有最左节点，则路径一直向右，直至最后的节点
            int num =  (int) (Math.pow(2,(level - 1))) + getCompleteTreeNodeNumber(head.right,level - 1);
//            System.out.println(level+" : "+(int) (Math.pow(2,(level - 2))));
            return num;
        }
        //如果没有，则右子树为满二叉树,右子树的高度为level - 2（比左子树的高度小1）,此时递归左子树
        //如果右子树为空，则高度
        //preOrder: 1!2!#!#!#!  则h = 2, 无右节点， 2^(2-2) = 1   2^(1-2) = 1/2 = int(0),所以会少了一个
        else{
            //注意java求指数不能用^，只能用pow函数
            int num = (int) (Math.pow(2,(level - 2))) + getCompleteTreeNodeNumber(head.left,level - 1);
//            System.out.println(level+" : "+(int) (Math.pow(2,(level - 2))));
            return num;
        }
    }

    //得到树的高度
    public int getLevel(AbstractNode head){

        int level;

        if(head == null){
            level = 0;
            return level;
        }

        level = 1;

        while(head.left != null){
            level += 1 ;
            head = head.left;
        }

        return level;
    }

    @Test
    public void test1(){

        AbstractNode head = new code0001_preInPosTraversal().generateTree(19);
        printTree(head);

        System.out.println("inOrder1:");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();

        int level = getLevel(head);
        System.out.println("level:" + level);

        int num = nodeNum(head);
        System.out.println("num:" + num);
    }
}
