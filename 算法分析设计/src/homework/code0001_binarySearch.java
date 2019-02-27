package homework;

import org.junit.Test;

public class code0001_binarySearch {

    public int[] binarySearch(int array[],int aim){

        int left = 0;
        int right = array.length - 1;
        int middle = 0;


        while(left <= right){

            middle = left + (right - left)/2;

            if(aim == array[middle]){
                return new int[]{middle};
            }
            else if(aim < array[middle]){
                if(middle - 1 < 0){   //没有更小值
                    return new int[]{-1,middle};
                }
                right = middle - 1;  //right容易越界
            }
            else{
                if(middle + 1 > array.length - 1){ //没有更大值
                    return new int[]{middle,-1};
                }
                left = middle + 1;  //left容易越界
            }
        }

        //如果数组中没有该值，则执行到这里,规定0位填min_index,1填max_index

        int min = array[middle];
        int max = array[middle + 1];

        int result[] = {-1,-1};
        if(aim > min){
            result[0] = middle;
        }

        if(aim < min){
            result[1] = 0;
        }
        else if(aim < max){
            result[1] = middle + 1;
        }
        return result;
    }

    @Test
    public void binaryTest(){
        int array[] = {2,3,4,5,6,8,9};
        int aim[] = {1,10,7,8};
        int result[];

        for(int i = 0; i < aim.length; i++){
            result = binarySearch(array,aim[i]);

            if(result.length == 2){

                if(result[1] == -1){
                    System.out.println("不存在比" + aim[i] + "大的数组元素");
                }else{
                    System.out.println("比" + aim[i] + "大的最小数组元素的下标是" + result[1]);
                }

                if(result[0] == -1){
                    System.out.println("不存在比" + aim[i] + "小的数组元素");
                }else{
                    System.out.println("比" + aim[i] + "小的最大数组元素的下标是" + result[0]);
                }

            }else{
                System.out.print("与" + aim[i] + "相等的数据元素的下标是" + result[0]);
            }

            System.out.println();
        }

    }
}
