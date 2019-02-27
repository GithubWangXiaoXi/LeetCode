package com.wangxiaoxi.stack;

import java.util.Stack;

public class code0111_implementStackGetMin {

    /**
     *     实时获取stack中的最小值的功能：使用2个栈，data栈负责存data，
       min栈的栈顶与data栈的栈顶比较，如果data栈顶元素小，则存入data栈顶的元素。
       如果大，则重复存入min栈的栈顶元素
     */

    static class MyStack{
       private Stack<Integer> dataStack = null;
       private Stack<Integer> minStack = null;
       private int min;

       public MyStack(){
           dataStack = new Stack<Integer>();
           minStack = new Stack<Integer>();
       }

       //两个栈在push的时候，要保持两个栈的size要一样
       public void push(int num){

           dataStack.push(num);

           if(minStack.isEmpty()){
               minStack.push(num);
           }else{
               min = minStack.peek();
               //压入两个栈之间栈顶的最小值
               min = min < dataStack.peek() ? min : dataStack.peek();
               minStack.push(min);
           }
       }

       public int pop(){
           if(dataStack.isEmpty()){
               throw new ArrayIndexOutOfBoundsException("stack is empty");
           }
           int res = dataStack.pop();
           minStack.pop();
           return res;
       }

       public int peek(){
           if(dataStack.isEmpty()){
               throw new ArrayIndexOutOfBoundsException("stack is empty");
           }
           int res = dataStack.peek();
           return res;
       }

       public int getMin(){
           if(minStack.isEmpty()){
               throw new ArrayIndexOutOfBoundsException("stack is empty");
           }
           int res = minStack.peek();
           return res;
       }

       public void showTwoStackSize(){
           System.out.println("dataStack:"+dataStack.size());
           System.out.println("minStack:"+minStack.size());
       }
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        for(int i = 0;i<10;i++){
            int num = (int) (Math.random()*15);
            myStack.push(num);
            System.out.print(num+" ");
        }

        System.out.println("min:"+myStack.getMin());
        myStack.showTwoStackSize();
    }
}
