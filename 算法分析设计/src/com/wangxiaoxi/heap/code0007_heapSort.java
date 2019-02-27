package com.wangxiaoxi.heap;

import org.junit.Test;

import java.util.Arrays;

import static com.wangxiaoxi.Orders.code1000_correctCountMachine.copyArray;
import static com.wangxiaoxi.Orders.code1000_correctCountMachine.isEqualForArrays;

public class code0007_heapSort {

    public static void heapSort(int arr[]){
        for(int i = 0;i<arr.length;i++){
            insertHeap(arr,i);
        }
        int heapSize = arr.length - 1;
        for(int i = arr.length-1;i>=0;i--){
            swap(0,i,arr);
            //如果heapsize为1，有两个数，则不用heapify
            if(heapSize > 1){
                heapify(arr,heapSize);
                heapSize--;
            }
        }
    }

    //形成大根堆
    public static void insertHeap(int arr[],int index){
        //插入一个数，需要和它的头进行比较
        int headIndex = (index-1)/2;
        while(arr[index] > arr[headIndex]){
            swap(index,headIndex,arr);
            index = headIndex;
            headIndex = (index-1)/2;
        }
    }

    //如果头的数变小了，需要将序列调整成大根堆,注意在弹出堆顶元素时，堆的大小（heapSize）会变小
    public static void heapify(int arr[],int heapSize){
        int index = 0;
        int left = 1;
        while(left < heapSize){
            //如果不存在右孩子，则左孩子最大
            int largeChildIndex = (left+1 < heapSize) && arr[left] < arr[left+1] ? left+1 : left;
            if(arr[index] > arr[largeChildIndex]){
                break;
            }
            //交换父节点和子节点的值,每次只记录对应节点的left孩子，待会可以用哪个left孩子好heapSize比较，看是否越界
            else{
                swap(index,largeChildIndex,arr);
                index = largeChildIndex;
                left = largeChildIndex*2+1;
            }
        }
    }

    public static void swap(int i,int j,int arr[]){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void showArrays(int arr[]){
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    //随机数组产生器
    public static int[] generateArray(int size){
        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[(int) (Math.random()*temp)];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }


    public static void main(String[] args) {
         int arr[] = generateArray(10);
         System.out.print("example:");
         showArrays(arr);

         System.out.print("heap: ");
         heapSort(arr);
         showArrays(arr);
    }

    @Test
    public void test(){
        int testTimes = 10000;
        int exampleN = 10;
        boolean result = true;
        for(int i = 0;i<testTimes;i++){
            int arr[] = generateArray(10);
            int arr1[] = copyArray(arr);  //使用正确方法
            int arr2[] = copyArray(arr);   //输出原来的样本数据

            heapSort(arr);
            Arrays.sort(arr1);

            boolean tempResult = isEqualForArrays(arr,arr1);

            //如果有报错，则输出两个拍完序的序列，并输出测试用例
            if(tempResult == false){
                result = false;
                System.out.print("arr ");
                showArrays(arr);

                System.out.print("arr1 ");
                showArrays(arr1);

                System.out.print("arr2 ");
                showArrays(arr2);
                break;
            }
        }
        System.out.println(result?"Excellent":"awful");
    }
}
