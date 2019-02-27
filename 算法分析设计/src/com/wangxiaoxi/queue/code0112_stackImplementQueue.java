package com.wangxiaoxi.queue;

import java.util.Stack;

public class code0112_stackImplementQueue {

    /**
     *      用栈结构实现队列：实现思路：
     *      push栈负责存数据，pop栈负责依次存push栈中的数据，再在help栈中弹出data，实现队列先进先出的效果
     *   这时pop栈和push栈不存在互相引用，
     *   1，当pop栈不为空时，push栈中的数据不能添加到pop栈中，
     *   2，pop栈中为空时，才能一次性将push栈的数据压入pop栈中
     */

    static class MyQueue{

         private Stack<Integer> pushStack;
         private Stack<Integer> popStack;

         public MyQueue(){
             pushStack = new Stack<>();
             popStack = new Stack<>();
         }

         public void push(int num){
             pushStack.push(num);
             //可以将该操作命名为dao操作
//             if(popStack.isEmpty()){
//                while(pushStack.size()>0){
//                    int data = pushStack.pop();
//                    popStack.push(data);
//                }
//             }
             daoDataToPopStack();
         }

         public int poll(){
             //在使用栈和队列时要考虑周到，哪些情况不能弹出栈顶，队头的元素
             if(pushStack.isEmpty() && popStack.isEmpty()){
                 throw new ArrayIndexOutOfBoundsException("queue is empty");
             }
             daoDataToPopStack();
             int res = popStack.pop();
             return res;
         }

         public int peek(){
             if(pushStack.isEmpty() && popStack.isEmpty()){
                 throw new ArrayIndexOutOfBoundsException("queue is empty");
             }
             daoDataToPopStack();
             int res = popStack.peek();
             return res;
         }

         //    由于push和pop操作是随时都可以进行的
         //    所以从pushStack倒数据到popStack需要遵循的原则:如果popStack非空，
         // 则在push,pop,peek时，就不能将数据从pushStack导入popStack中
         public void daoDataToPopStack(){
             if(popStack.isEmpty()){
                 while(pushStack.size()>0){
                     int data = pushStack.pop();
                     popStack.push(data);
                 }
             }
         }
     }

     public static void main(String[] args) {
          MyQueue myQueue = new MyQueue();
         for(int i = 0;i < 5;i++){
             int num = (int) (Math.random()*15);
             myQueue.push(num);
             System.out.print(num+" ");
         }
         System.out.println();

         for(int i = 0;i < 5;i++){
             System.out.print(myQueue.poll()+" ");
         }
     }
}
