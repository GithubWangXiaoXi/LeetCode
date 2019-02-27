package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0003_serializeAndReconstructTree {

    //将二叉树的某种遍历的输出结果序列化，然后可以通过对应的遍历方式反序列化字符串，重新建立该树
    //先序遍历的输出结果如下 1_2_#_#_3_#_#_

    class Node extends AbstractNode{

        public Node(int value) {
            super(value);
        }
    }

    //先序遍历序列化树
    public String preSerializeTree(AbstractNode head){

        if(head == null){
           return "#!";
        }

        String serializedStr = head.value + "!";
        serializedStr += preSerializeTree(head.left);  //左子树合并形成的serializedStr作为返回值添加到该方法中的str中
        serializedStr += preSerializeTree(head.right);
        return serializedStr;
    }

    public String preSerializeTree1(AbstractNode head){

        Stack<AbstractNode> stack = new Stack<>();
        stack.push(head);
        String str = "";

        while(!stack.isEmpty()){


            head = stack.pop();   //取出1个，压入2个。直到叶子节点，只弹出，没压入
            str += head.value + "!";

            if(head.right != null){
                stack.push(head.right);
            }else{
                str += "#!";
            }

            if(head.left != null){
                stack.push(head.left);
            }else{
                str += "#!";
            }
        }
        return str;
    }


    //通过之前先序遍历序列化得到的字符串，再次使用先序遍历重新构建一棵树
    public AbstractNode preReConstruct(String str){

        String token[] = str.split("!");

        if(token.length == 0){
            return null;
        }

        Queue<String> queue = new LinkedList<>();
        for(int i = 0; i < token.length; i++){
            queue.offer(token[i]);
        }

        AbstractNode head = preByQueue(queue);
        return  head;
    }

    //之前用递归序列化，反序列化时也用递归。序列化的串先依次记录左节点的信息，再依次记录右节点的信息，如果用压入自定义栈的方式反序列化，则无法使上面的右节点保存在栈的底部，非递归反序列化比较难实现
    public AbstractNode preByQueue(Queue<String> queue) {

        String value = queue.poll();

        if("#".equals(value)){
            return null;
        }

        AbstractNode head = new Node(Integer.parseInt(value)); //创建根节点
        head.left = preByQueue(queue);  //在左子树各节点创建成功下，根节点的左节点连上左子树
        head.right = preByQueue(queue);  //在右子树各节点创建成功下，根节点的右节点连上右子树
        return head;
    }

//    public AbstractNode preByQueue1(Queue<String> queue) {
//
//        AbstractNode tempNode; //用来记录子树的根节点，方便弹出的节点与之相连
//        Stack<AbstractNode> stack = new Stack<>();
//
//        String value = queue.poll();
//        AbstractNode head = new Node(Integer.parseInt(value)); //创建根节点
//
//        while(!queue.isEmpty()){
//
//            //先边压入左节点，直至遇到#，表示为空，不压入
//            while(!"#".equals(queue.peek())){
//                AbstractNode node = new Node(Integer.parseInt(queue.poll()));
//                stack.push(node);
//            }
//
//            //从最小的子树开始合成,没棵子树都有2个子节点
//            AbstractNode node = stack.pop();
//            node.left = null;  //左节点为null
//
//            queue.poll();  //清掉"#"队列首元素
//
//            if("#".equals(queue.peek())){  //判断右节点
//                node.right = null;
//                queue.poll();
//            }else{
//                node.right = new Node(Integer.parseInt(queue.poll()));
//                //如果node根节点右节点非空，则重新将node压回栈中，这样就可以先把右树搭建完，node就能连上了。
//            }
//        }
//    }

    @Test
    public void serializeTreeTest(){
         AbstractNode head = new code0001_preInPosTraversal().generateTree(10);
         printTree(head);

         System.out.println("preOrder:");
         new code0001_preInPosTraversal().preOrderRecur(head);
         System.out.println();

         System.out.println("preOrder-SerialiedStr:");
         String str = preSerializeTree(head);
         System.out.println(str);
         String str1 = preSerializeTree1(head);
         System.out.println(str1);

         AbstractNode head1 = preReConstruct(str);
         System.out.println("反序列化得到的二叉树为:");
         printTree(head1);
    }
}
