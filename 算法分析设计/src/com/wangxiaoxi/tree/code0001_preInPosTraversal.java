package com.wangxiaoxi.tree;

import com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree;
import com.wangxiaoxi.tree.treeUtils.AbstractNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.wangxiaoxi.tree.treeUtils.code1000_printBinaryTree.printTree;

public class code0001_preInPosTraversal {

    /**实现树的先序，中序，后序遍历*/
    /**
     *   先序遍历：中，左，右
     *   中序遍历：左，中，右
     *   后序遍历：左，右，中
     */

    //    继承AbstractNode，待会在创建对象时，用该类中的Node对象，而不用treeUtils中的Node类创建对象，解耦
    //接受的参数为AbstractNode类型
    class Node extends AbstractNode{

        public Node(int value){
            super(value);
        }
    }

    //使用递归的方法，递归的特点，每个节点都会经过3次（栈: a,a1,a2 : a会经过3次，第一次进来，a1,a2结束返回共2次）
    //先序遍历
    public void preOrderRecur(AbstractNode node){

        if(node == null){
            return;
        }

        System.out.print(node.value+"  "); //先输出根结点
        preOrderRecur(node.left); //成功打印左子树序列  （子问题和母问题等价，只不过子树比母树小而已）
        preOrderRecur(node.right);  //成功打印右子树序列
    }

    //中序遍历
    public void inOrderRecur(AbstractNode node){

        if(node == null){
            return;
        }

        inOrderRecur(node.left);  //中序的左子树已经成功打印好序列
        System.out.print(node.value+"  ");  //打印根节点
        inOrderRecur(node.right);  //中序的右子树已经成功打印好序列
    }

    //后序遍历
    public void postOrderRecur(AbstractNode node){

        if(node == null){
            return;
        }

        postOrderRecur(node.left);   //成功打印左序列
        postOrderRecur(node.right);   //成功打印右序列
        System.out.print(node.value+"  ");   //打印根节点
    }

    //  非递归实现先序，中序，后序遍历，此时就需要研究先序，中序，后序的实际走法了（但是最终还是能像递归一样归纳成宏观思路），而递归方式不用这么考虑！！！
    //  先序遍历：先打印根节点，再打印左子树，再打印右子树。 先沿最左的节点的路径打印，到达最左节点，然后到最左节点所在的子树的父节点的右节点，依次往上走，左子树走完，走右子树
    //  实现方法：使用一个栈，先压入父节点，弹出，依次压入父节点的右节点，左节点，这样在弹出时，先得到左节点，处理完左节点为父节点的子树后，再来处理刚才压入的右节点
    //这样思路和递归的方式差不多，先中，再从左子树得到正确的序列，再从右子树中得到正确的序列

    public void preOrderRecur1(AbstractNode head){

        Stack<AbstractNode> stack = new Stack<>();
        stack.push(head);
        int maxSize = Integer.MIN_VALUE;

        while(!stack.isEmpty()){

            if(maxSize < stack.size()){
                maxSize = stack.size();
            }

            head = stack.pop();   //取出1个，压入2个。直到叶子节点，只弹出，没压入
            System.out.print(head.value + "  ");  //先打印头结点

            if(head.right != null){
                stack.push(head.right);
            }

            if(head.left != null){
                stack.push(head.left);
            }
        }
        System.out.println("stackMaxSize:" + maxSize);
    }

    // 中序遍历: 先找左子树，左子树又要找比它小的左子树，直到得到最小的左子树（1个节点，好像归并排序最后merge的最小单位：1个数（left == right）），最小的左子树需要回去找父节点，才能得到右节点，这样一棵树又逐渐变大。
    // 对于根节点的左子树，先变得最小，再变得和原来左子树一样大
    // 实现思路：根节点不能先打印，需要找左节点，依次移动到最左的节点，即为最小子树的左节点，打印。由于没有规定是完全二叉树，所以到最左的节点后，到父节点（弹出，打印），再到右节点，也许右节点还存在更小的子树，需要去找，也是到最左节点（右节点的子树也是以同样的处理思路找到最左的节点，并打印）。待右子树的下半部分找完后，和之前的左子树规约成高度+1的子树，再规约...,把树变大
    public void inOrderRecur1(AbstractNode head){

        Stack<AbstractNode> stack = new Stack<>();
        if(head.left == null){
            System.out.println(head.value + " ");
            return;
        }

        stack.push(head);
        boolean flag = false;
        //需要判断head是否为空，如果没判断，会导致当stack取出根节点时，栈为空，右子树还没有遍历
        while(!stack.isEmpty() || head != null){
            //先到达最左节点
            while(head != null){
                if(flag){
                    stack.push(head);
                }
                head = head.left;
                flag = true;
            }
            //到达某一子树的最左节点后，弹出栈顶节点，寻找右节点
            head = stack.pop();
            System.out.print(head.value + "  "); //先打印左节点
            head = head.right;
        }

    }

    //后序遍历：左，右，中：由于 中，右，左比较容易实现(先压入左，再压入右，输出右左）。所以将其结果保存在一个栈中，逆序弹出（有些时候根据实际的输出顺序寻找解决方法比较困难时，可以考虑逆序输出，最后用栈将其顺序）
    public void postOrderRecur1(AbstractNode head){

        Stack<AbstractNode> stack1 = new Stack<>();
        Stack<Integer> helpStack = new Stack<>();

        stack1.push(head);

        while(!stack1.isEmpty()){

            Node node = (Node) stack1.pop();
            helpStack.push(node.value);  //将结果添加到辅助栈中

            if (node.left != null){
                stack1.push(node.left);
            }
            if (node.right != null){
                stack1.push(node.right);
            }
        }

        while(!helpStack.isEmpty()){
            System.out.print(helpStack.pop()+"  ");
        }
    }


    //   根据节点总数，随机生成一棵包含左右节点的二叉树
    //       1，首先树的高度i满足: 2^i - 1 < num < 2^(i+1) - 1
    //       2，生成方法:创建父节点，压入栈，取出，并创建该节点的左右孩子节点，然后先将右节点压入栈，再压入左节点--
    //   使用栈方法不可取，这样会导致先将左边寻找（创建）到底，再来寻找（创建）右边的。而实际上通过节点，逐层创建树才能保证每次创建的树是个完全二叉树
    //       3，所以使用队列，将父节点压入队列，弹出，将左右节点依次压入队列，再弹出。。
    //       如果节点个数为奇数：则每个节点都有左右孩子
    //       如果节点个数为偶数：则最后一个叶子节点的父节点没有右节点
    public AbstractNode generateTree(int num){

        Queue<AbstractNode> queue = new LinkedList<>();

        AbstractNode headNode = new Node((int) (Math.random()*20 + 1));
        queue.offer(headNode);
        //每次从queue中取出1个数，并创建2个数，所以实际循环次数为num / 2!!!,但num有奇偶之分
        for(int i = 0; i < num/2 ; i++){

            AbstractNode node = queue.poll();
            Node leftNode = new Node((int) (Math.random()*20 + 1));
            Node rightNode = new Node((int) (Math.random()*20 + 1));

            node.left = leftNode;

            //如果num为偶数，则无右孩子
            if(i == num/2 - 1 && num%2 == 0){
                break;
            }else{
                node.right = rightNode;
            }

            //先将左节点加入队列
            queue.offer(leftNode);

            //再将右节点加入队列
            queue.offer(rightNode);
        }

        return headNode;
    }

    @Test
    public void printFullTreeTest(){

        AbstractNode head = generateTree(6);
        printTree(head);

        System.out.println("preOrder: ");
        preOrderRecur(head);
        System.out.println();
        preOrderRecur1(head);
        System.out.println();

        System.out.println("inOrder: ");
        inOrderRecur(head);
        System.out.println();
        inOrderRecur1(head);
        System.out.println();

        System.out.println("postOrder: ");
        postOrderRecur(head);
        System.out.println();
        postOrderRecur1(head);
        System.out.println();
    }
}
