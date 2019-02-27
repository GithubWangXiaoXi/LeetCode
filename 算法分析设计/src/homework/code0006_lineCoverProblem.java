package homework;

import java.util.Arrays;
import java.util.Comparator;

public class code0006_lineCoverProblem {

    /**
     * 线段覆盖的长度
     * 每个点有起始和终止坐标
     *
     *1，先对coord按起始坐标排序，
     *2，然后用当前coord的终止坐标 - 下一个coord的起始坐标
     */

    static class Coordinate  {
        int start;
        int end;

        public Coordinate(int start,int end){
            this.start = start;
            this.end = end;
        }
    }

    static class MyComparator implements Comparator<Coordinate>{

        @Override
        public int compare(Coordinate o1, Coordinate o2) {
            // TODO Auto-generated method stub
            return o1.start - o2.start;
        }
    }


    public static int getCoveredLineLength(Coordinate coordinates[]){

        int coveredLineLength = 0;
        for(int i = 0; i < coordinates.length - 1; i++){
            if(coordinates[i].end > coordinates[i + 1].start){
                coveredLineLength += coordinates[i].end - coordinates[i + 1].start;
            }
        }
        return coveredLineLength;
    }

    public static void main(String argvs[]){

        int num = 10;
        Coordinate coordinates[] = new Coordinate[num];

        Coordinate coordinate1 = new Coordinate(2,3);
        Coordinate coordinate2 = new Coordinate(3,5);
        Coordinate coordinate3 = new Coordinate(4,7);
        Coordinate coordinate4 = new Coordinate(5,6);
        Coordinate coordinate5 = new Coordinate(6,9);
        Coordinate coordinate6 = new Coordinate(7,8);
        Coordinate coordinate7 = new Coordinate(8,12);
        Coordinate coordinate8 = new Coordinate(9,10);
        Coordinate coordinate9 = new Coordinate(10,13);
        Coordinate coordinate10 = new Coordinate(11,15);

        coordinates[0] = coordinate1;
        coordinates[1] = coordinate2;
        coordinates[2] = coordinate3;
        coordinates[3] = coordinate4;
        coordinates[4] = coordinate5;
        coordinates[5] = coordinate6;
        coordinates[6] = coordinate7;
        coordinates[7] = coordinate8;
        coordinates[8] = coordinate9;
        coordinates[9] = coordinate10;

        Arrays.sort(coordinates,new MyComparator());

        int coveredLineLength = getCoveredLineLength(coordinates);

        System.out.println("线段覆盖长度为:" + coveredLineLength);
    }
}
