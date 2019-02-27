package com.wangxiaoxi.recursion.violence_recursion;

import org.junit.Test;

public class code0003_exhaustiveArray {

    /**问题描述：打印一个字符串的全部子序列：字符串中每个字符有2种选择（要打印，不要打印（打印空格））
     *
     * 实现方法：
     * 通过递归的方式：去尝试，枚举每个字符的所有可能性
     */

    //字符串
    public void exhaustiveSubStr(String str, int index, String result){

        if(index == str.length()){
            System.out.println(result);
            return;
        }
        else{
            //要该字符,结果 + 该字符
            exhaustiveSubStr(str,index + 1,result + str.charAt(index));

            //不要该字符，结果 + 空格
            exhaustiveSubStr(str,index + 1,result + " ");
        }
    }

    /**
     * 数字全排列：
     * 如果有2个数 1 2，全排列则为 1 2/ 2 1
     * 如果有3个数1，2，3，则在1的基础上3 1 2/ 1 3 2/ 1 2 3 或者 3 2 1/2 3 1/2 1 3
     */
    public void exhaustiveNumArray(Integer array[], int index){

        if(index == array.length){
            showArray(array);
            return;
        }

        //当前的数要实现全排列:只需要将当前数X，插入到假设已经全排列的子序列中即可(实际上是与各个位置上的数进行交换,但是每次交换后，需要恢复原状)
        //但是这样有点难理解，如果理解成序列划分成子序列，直到最后一个元素，然后回溯，这样全排列从小数开始，最后合并成一棵大树。
        for(int i = index; i < array.length; i++){
            swap(i,index,array);  //一开始自己与自己交换，第一次到最底时，输出原来的序列（递归栈中index不变，i增加，回溯打印）
            exhaustiveNumArray(array,index + 1);
            swap(i,index,array);
        }
    }

    public void showArray(Integer array[]){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void swap(int i,int j,Integer[] array){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Test
    public void subStrTest(){
        String abc = "abc";
        exhaustiveSubStr(abc,0,"");
    }


    @Test
    public void exhaustiveNumArrayTest(){
        Integer array[] = {1,2,3,4};
        exhaustiveNumArray(array,0);
    }
}
