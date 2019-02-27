package com.wangxiaoxi.stack;

import java.util.LinkedList;
import java.util.Queue;

public class code0112_queueImplementStack {

    /**
     *     用队列来实现栈结构：实现思路
     * data队列来存取data，help队列来辅助存储data中的前N-1个数,这样data队列可以将
     * 最后进入的元素弹出，再将help队列中的N-1个数输入到data队列中，此时data，help队列
     * 可以互相取代位置
     */

    static class MyStack{
        Queue<Integer> dataQueue;
        Queue<Integer> helpQueue;

        public MyStack(){
            //用双向链表实现队列
            dataQueue = new LinkedList<>();
            helpQueue = new LinkedList<>();
        }

        public void push(int num){
            dataQueue.add(num);
        }

        //弹出栈顶元素，需要将dataQueue中的N-1个元素一次性添加到help中，弹出dataQueue的末尾元素
        public int pop(){
            if(dataQueue.isEmpty()){
                throw new ArrayIndexOutOfBoundsException("stack is empty");
            }
            while(dataQueue.size() > 1){
                int data = dataQueue.poll();
                helpQueue.add(data);
            }
            int res = dataQueue.poll();
            //help队列中存有N-1个数据，这时help可以充当data队列，而data队列为空。
            swapCite();
            return res;
        }

        public int peek(){
            if(dataQueue.isEmpty()){
                throw new ArrayIndexOutOfBoundsException("stack is empty");
            }
            while(dataQueue.size() > 1){
                int data = dataQueue.poll();
                helpQueue.add(data);
            }
            int res = dataQueue.peek();
            helpQueue.add(res);

            //help队列中存有N-1个数据，这时help可以充当data队列，而data队列为空。
            swapCite();

            return res;
        }

        public void swapCite(){
            Queue<Integer> temp = dataQueue;
            dataQueue = helpQueue;
            helpQueue = temp;
        }
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        for(int i = 0;i < 5;i++){
            int num = (int) (Math.random()*15);
            myStack.push(num);
            System.out.print(num+" ");
        }
        System.out.println();

        for(int i = 0;i < 5;i++){
            System.out.print(myStack.pop()+" ");
        }
    }
}
