package com.wangxiaoxi.queue;

import com.wangxiaoxi.stack.code0110_arrMakeStack;

public class code0111_arrMakeQueue {

    //队列是先进先出的，在数组中的实现像循环数组一样（减少了值的移动）
    static class Queue{

        int start;  //弹出队列头元素的指针
        int end;    //添加元素到队列的指针
        int queueSize;
        int queueMaxSize;   //可以有queue.length来取代
        int queue[] = null;

        public Queue(int arraySize) {
            queueMaxSize = arraySize;
            queue = new int[queueMaxSize];
            start = 0;
            end = 0;
            queueSize = 0;
        }

        //入队列
        public void push(int num){
            if(queueSize == queueMaxSize){
                throw new ArrayIndexOutOfBoundsException("queue is full");
            }
            queue[end] = num;
            end = end == queueMaxSize-1 ? 0 : end+1;
            queueSize++;
        }

        //弹出队列头元素
        public int pop(){
            if(queueSize == 0){
                throw new ArrayIndexOutOfBoundsException("queue is empty");
            }
            int queueTop =  queue[start];
            start = start == queueMaxSize ? 0 :start+1;
            queueSize--;
            return queueTop;
        }

        //得到队列头元素
        public int peek(){
            if(queueSize == 0){
                throw new ArrayIndexOutOfBoundsException("queue is empty");
            }
            int queueTop =  queue[start];
            return queueTop;
        }
//  队列是一头进，一头出，头和尾都是动态的，头的下标有可能比尾的下标小，无法正确输出
//        public void showStack(){
//            for(int i = start;i<end;i++){
//                System.out.print(queue[i]+" ");
//            }
//        }
    }

    public static void main(String[] args) {
        Queue queue = new Queue(4);
        for(int i = 0;i<4;i++){
            int num = (int) (i+Math.random()*10);
            queue.push(num);
            System.out.print(num+" ");
        }

        System.out.println();

        for(int i = 0;i<5;i++){
            System.out.println(queue.pop());
        }
    }
}
