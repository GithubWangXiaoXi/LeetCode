package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import org.junit.Test;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0004_isBalancedTree {

    class Node extends AbstractNode {

        public Node(int value) {
            super(value);
        }
    }

    class ReturnData{
        int height;
        Boolean isBalanced;

        public ReturnData(int height, Boolean isBalanced){
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }

    //判断是否是平衡树，计算左右子树的高度以及是否是平衡，保存在returnData中。
    public ReturnData isBalancedTree(AbstractNode head){

        //如果为空树，也是平衡树,记得写上递归的中止条件，否则到达树的叶子时，left，right会抛出空指针异常！！
        if(head == null){
            return new ReturnData(0,true);
        }

        ReturnData left = isBalancedTree(head.left);  //得到左子树的平衡信息
        ReturnData right = isBalancedTree(head.right);  //得到右子树的平衡信息

        //如果左右子树都不平衡，返回false
        if(left.isBalanced == false){
            return new ReturnData(0,false);
        }
        if(right.isBalanced == false){
            return new ReturnData(0,false);
        }

        //如果左右子树都平衡，则判断左右子树高度的差值是否>1
        if(Math.abs(left.height - right.height) > 1){
            return new ReturnData(0,false);
        }

        //如果子树平衡，则高度为左右子树最高+1
        return new ReturnData(Math.max(left.height,right.height) + 1,true);
    }

    @Test
    public void isBalancedTreeTest(){
        AbstractNode head = new code0001_preInPosTraversal().generateTree(18);
        printTree(head);

        System.out.println("preOrder:");
        new code0001_preInPosTraversal().preOrderRecur(head);
        System.out.println();

        ReturnData isBalancedTree = isBalancedTree(head);
        System.out.println("是否是二叉平衡树:" + isBalancedTree.isBalanced);
        System.out.println("高度为:" + isBalancedTree.height);
    }
}
