package com.wangxiaoxi.recursion;

import org.junit.Test;

public class QuanArrayTest {


     public void perimOfReCursion(int begin,int last,Integer[] array){
          //如果只有1个数(原子性)，该序列不能再次交换，形成新的序列，则将整个序列输出。
          if(begin == last){
               for(int i = 0;i<array.length;i++) {
                    System.out.print(array[i]+" ");
               }
               System.out.println();
          }else{
               //如果不止2个数，则将序列串继续划分成子串，每次依次向后找数，替换掉最前面的数
               for(int i = begin;i<last;i++){
                    swap(i,begin,array);
                    perimOfReCursion(begin+1,last,array);
                    //需要将序列变回来，回到初始串的状态，这样才能继续依次去找数替换最前面的数
                    swap(i,begin,array);
               }
          }
     }

     public void swap(int i,int j,Integer[] array){
          int temp = array[i];
          array[i] = array[j];
          array[j] = temp;
     }

     @Test
     public void test(){
          Integer[] array = {1,2,3,4,5};
          perimOfReCursion(0,5,array);
     }

     public void perimOfReCircle(int begin,int last,Integer[] array){

//          int augment = last - begin;  3-0=3   augment = 0，1

          for(int i = begin; i<last; i++){
               swap(i,begin,array);
               for(int j = begin+1; j<last; j++){
                    swap(begin+1,j,array);
                    showArray(last,array);
                    swap(begin+1,j,array);
               }
          }
     }

     //用for循环思想实现全排列难实现，用递归较好！！
//     public void perimOfReCircle1(int begin,int last,Integer[] array){
//
//          int maxAugment = last - begin - 2;
//          int minAugment = -1;
//
//          while(maxAugment >= minAugment){
//               maxAugment--;
//               for(int i = maxAugment; i<last; i++){
//                    swap(i,begin,array);
//                    showArray(last,array);
//                    swap(i,begin,array);
//               }
//          }
//     }

     public void showArray(int length,Integer[] array){
          for(int i = 0;i<length;i++){
               System.out.print(array[i]+"  ");
          }
          System.out.println();
     }

     @Test
     public void test1(){
          Integer[] array = {1,2,3};
          perimOfReCircle(0,3,array);
     }

//     @Test
//     public void test2(){
//          Integer[] array = {1,2,3,4,5};
//          perimOfReCircle1(0,5,array);
//     }
}
