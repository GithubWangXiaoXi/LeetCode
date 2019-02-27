package com.wangxiaoxi.recursion;

public class codeR_0001_getMax {

    //使用分治的思想找出最大的元素
    public static int getMax(int left,int right,int arr[]){

        if(left == right){
            return arr[left];
        }
        else{
            int middle = (left+right)/2;
            int leftMax = getMax(left,middle,arr);
            int rightMax = getMax(middle+1,right,arr);
            int result = Math.max(leftMax,rightMax);
            return result;
        }
    }

    //使用分治的思想找出最小的元素
    public static int getMin(int left,int right,int arr[]){

        if(left == right){
            return arr[left];
        }
        else{
            int middle = (left+right)/2;
            int leftMax = getMin(left,middle,arr);
            int rightMax = getMin(middle+1,right,arr);
            int result = Math.min(leftMax,rightMax);
            return result;
        }

    }

    public static void main(String[] args) {
        int arr[] = {2,8,3,3,4,5,6,9};
        int maxNum = getMax(0,arr.length-1,arr);
        int minNum = getMin(0,arr.length-1,arr);

        System.out.println("maxNum "+maxNum);
        System.out.println("minNum "+minNum);
    }
}
