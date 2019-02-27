package com.wangxiaoxi.tree.treeUtils;


import java.util.LinkedList;
import java.util.Queue;

public class GenerateTreeWithParent {


    class Node extends AbstractNode{

        public Node(int value) {
            super(value);
        }
    }

    //   根据节点总数，随机生成一棵包含左右节点的二叉树
    //       1，首先树的高度i满足: 2^i - 1 < num < 2^(i+1) - 1
    //       2，生成方法:创建父节点，压入栈，取出，并创建该节点的左右孩子节点，然后先将右节点压入栈，再压入左节点--
    //   使用栈方法不可取，这样会导致先将左边寻找（创建）到底，再来寻找（创建）右边的。而实际上通过节点，逐层创建树才能保证每次创建的树是个完全二叉树
    //       3，所以使用队列，将父节点压入队列，弹出，将左右节点依次压入队列，再弹出。。
    //       如果节点个数为奇数：则每个节点都有左右孩子
    //       如果节点个数为偶数：则最后一个叶子节点的父节点没有右节点


    //创建一棵有parent指针的树
    public AbstractNode generateTreeWithParent(int num){

        Queue<AbstractNode> queue = new LinkedList<>();

        AbstractNode headNode = new Node((int) (Math.random()*20 + 1));
        queue.offer(headNode);
        //每次从queue中取出1个数，并创建2个数，所以实际循环次数为num / 2
        for(int i = 0; i < num/2 ; i++){

            AbstractNode node = queue.poll();
            Node leftNode = new Node((int) (Math.random()*20 + 1));
            Node rightNode = new Node((int) (Math.random()*20 + 1));

            node.left = leftNode;
            leftNode.parent = node;

            //如果num为偶数，则无右孩子
            if(i == num/2 - 1 && num%2 == 0){
                break;
            }else{
                node.right = rightNode;
                rightNode.parent = node;
            }

            //先将左节点加入队列
            queue.offer(leftNode);

            //再将右节点加入队列
            queue.offer(rightNode);
        }

        return headNode;
    }
}
