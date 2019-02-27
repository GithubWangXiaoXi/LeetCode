package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0005_serializedByLayers {

    class Node extends AbstractNode {

        public Node(int value) {
            super(value);
        }
    }

    /**将树的结果按层序列化*/
    public String serializedByLayers(AbstractNode head){

         Queue<AbstractNode> queue = new LinkedList<>();

         String str = "";
         queue.offer(head);
         str += head.value + "!";  //记录第一层的信息

         //弹出1个，压入2个，压入之前先写入到序列化str中(记录每一层的信息)，起始:记录第二层信息
         while(!queue.isEmpty()){

             AbstractNode node = queue.poll();

             if(node.left != null){
                 str += node.left.value + "!";
                 queue.offer(node.left);
             }else{
                 str += "#!";
             }

             if(node.right != null){
                 str += node.right.value + "!";
                 queue.offer(node.right);
             }else{
                 str += "#!";
             }
         }
         return str;
    }

    /**将树的结果按层反序列化*/
    public AbstractNode reconstruct(String str){

        String token[] = str.split("!");

        if(token.length == 0){
            return null;
        }

        Queue<String> queue = new LinkedList<>();
        for(int i = 0; i < token.length; i++){
            queue.offer(token[i]);
        }

        AbstractNode head = reSerializedByLayers(queue);
        return head;
    }

    //如果按层反序列化构建树，每个非空节点都有两个子节点（null）
    public AbstractNode reSerializedByLayers(Queue<String> nodeQueue) {

        Queue<AbstractNode> queue = new LinkedList<>();   //创建树使用的辅助队列，而nodeQueue则是保存着各层节点的信息

        int num = nodeQueue.size();

        AbstractNode head = new Node(Integer.parseInt(nodeQueue.poll()));
        queue.offer(head);

        //queue中每次弹出的节点都有2个子节点（#也为一个空节点）
        //弹1个，压2个，如果2个都为空，则没有压入，这样会使queue变空
        while(!queue.isEmpty()){

            AbstractNode node = queue.poll();

            AbstractNode leftNode;
            AbstractNode rightNode;

            //如果不是null节点，则将节点压入队列，并连接到node的left上
            if(!"#".equals(nodeQueue.peek())){
                leftNode = new Node(Integer.parseInt(nodeQueue.poll()));
                node.left = leftNode;
                queue.offer(leftNode);
            }
            //如果是空节点，则不压入队列，将node的left置为null
            else{
                node.left = null;
            }

            if(!"#".equals(nodeQueue.peek())){
                rightNode = new Node(Integer.parseInt(nodeQueue.poll()));
                node.right = rightNode;
                queue.offer(rightNode);
            }else{
                node.right = null;
            }
        }

        return head;
    }

    @Test
    public void serializedByLayersTest(){
        AbstractNode head = new code0001_preInPosTraversal().generateTree(18);
        printTree(head);

        System.out.println("preOrder:");
        new code0001_preInPosTraversal().preOrderRecur(head);
        System.out.println();

        String str = serializedByLayers(head);
        System.out.println("serializedByLayers:");
        System.out.println(str);

        System.out.println("ReSerializedByLayers:");
        AbstractNode head1 = reconstruct(str);
        printTree(head1);
    }
}
