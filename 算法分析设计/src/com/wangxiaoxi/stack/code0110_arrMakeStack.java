package com.wangxiaoxi.stack;

public class code0110_arrMakeStack {

    static class Stack{
        private int index;
        private int[] stack = null;
        private int stackMaxSize;

        public Stack(int arraySize) {
            stackMaxSize = arraySize;
            stack = new int[stackMaxSize];
            index = 0;
        }

        //入栈
        public void push(int num){
            if(index == stackMaxSize){
                //如果越界会抛异常，运行中断
               throw new ArrayIndexOutOfBoundsException("stack is full");
            }
            stack[index++] = num;

        }

        //弹出栈顶元素
        public int pop(){
            if(index <= 0){
               throw new ArrayIndexOutOfBoundsException("stack is empty");
            }
            int element = stack[--index];
            return element;

        }

        public int peek(){
            if(index == 0){
                return 0;
            }else{
                int element = stack[index-1];
                return element;
            }
        }

        public void showStack(){
            for(int i = 0;i<index;i++){
                System.out.print(stack[i]+" ");
            }
        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack(3);
        for(int i = 0;i<3;i++){
            stack.push(i);
        }
        stack.showStack();
        System.out.println();

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.showStack();

        for(int i = 5;i<9;i++){
            stack.push(i);
        }
        stack.showStack();
    }
}
