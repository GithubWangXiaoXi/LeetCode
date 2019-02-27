package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import com.wangxiaoxi.tree.treeUtils.GenerateTreeWithParent;
import org.junit.Test;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0002_successorNode {

    /**在拥有parent指针节点的二叉树中寻找某节点的后继/先驱,要求时间复杂度小于O(N),则无需遍历整棵树，求其中序遍历*/

    //实现思路（一般找中间的数研究，比较有代表性）：
    // 如果查找的数X有右节点，则X的后继节点为右节点的最左边的节点。
    // 如果查找的数X无右节点，则判断X的父节点的左节点是不是X，如果不是X变成父节点，依次往上找
    class Node extends AbstractNode{

        public Node(int value){
            super(value);
        }

    }

    //得到该节点的后继节点
    public AbstractNode getSuccessorNode(AbstractNode node){

        //如果node有右节点,则node的后继节点为右节点的最左的节点
        AbstractNode temp = node.right;
        if (temp != null){
            while(temp != null){
                node = temp;
                temp = temp.left;
            }
            return node;
        }
        //如果node无右节点，则判断node的父节点的左节点是不是node，如果不是X变成父节点，依次往上找
        else{
            AbstractNode p1 = node.parent;
            while(p1.left != node && p1 != null){
                node = p1;
                p1 = node.parent;
            }

            return p1;
        }
    }

    //得到该节点的前驱节点
    public AbstractNode getPriorNode(AbstractNode node){

        //如果是该节点有左孩子，则前驱为左子树的最右的节点

        if(node.left != null){

             AbstractNode temp = node.left;

             while (temp != null){
                 node = temp;
                 temp = temp.right;
             }

             return node;
        }
        //如果该节点node没有左孩子，则判断node的父节点的右孩子是不是node，如果不是依次往上走
        else{

            AbstractNode temp = node.parent;
            while(temp.right != node && temp != null){
                 node = temp;
                 temp= temp.parent;
            }

            return temp;
        }
    }

    @Test
    public void getSuccessorNodeTest(){

        AbstractNode head = new GenerateTreeWithParent().generateTreeWithParent(15);
        printTree(head);

        System.out.println("inOrder: ");
        new code0001_preInPosTraversal().inOrderRecur(head);
        System.out.println();
        new code0001_preInPosTraversal().inOrderRecur1(head);
        System.out.println();

        AbstractNode node = head.left.right;

        System.out.println("node:" + node.value + "的后继节点为:");
        AbstractNode successorNode = getSuccessorNode(node);
        System.out.println(successorNode.value);

        System.out.println("node:" + node.value + "的前驱节点为:");
        AbstractNode priorNode = getPriorNode(node);

        System.out.println(priorNode.value);
    }
}
