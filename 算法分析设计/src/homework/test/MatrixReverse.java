package homework.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MatrixReverse{

    private static final int MaxSize = 1<<25;
    private static int m,n;
    private static int sour,dest;
    private static int[] row,col;
    private static int[] hash = new int[MaxSize];
    private static int[] ans = new int[MaxSize];
    private static int X;

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        while (true){
            if(!init(input)){
                System.out.println("No Solution!");
                return;
            }
            if(fifobb()){
                System.out.println(hash[dest]);
                output(dest);
            }
        }
    }

    private static boolean init(Scanner input){
        int i;
        int a=0,b=0;

        m = input.nextInt();
        n = input.nextInt();

        row = new int[m];
        col = new int[n];

        String[] src = new String[m];
        String[] des = new String[m];
        char[] srcChars = new char[m*n];
        char[] desChars = new char[m*n];

        for(i=0; i<m; i++){
            src[i] = input.next();
            System.arraycopy(src[i].toCharArray(),0,srcChars,n*i,n);
        }

        for(i=0; i<m; i++){
            des[i] = input.next();
            System.arraycopy(des[i].toCharArray(),0,desChars,n*i,n);
        }

        for(sour=i=0; i<m*n; i++) {sour|=(srcChars[i]-'0')<<i; a+=srcChars[i]-'0';}
        for(dest=i=0; i<m*n; i++) {dest|=(desChars[i]-'0')<<i; b+=desChars[i]-'0';}

        return a==b;
    }

    private static boolean fifobb(){
        Queue<Integer> Q = new LinkedList<>();
        int E = 0;
        int N = 0;
        for(int i=0; i<MaxSize; i++) hash[i]=-1;
        hash[sour] = 0;
        Q.add(sour);
        while (!Q.isEmpty()){
            E = Q.poll();
            for(int i=0; i<m-1; i++)
                for(int j=i+1; j<m; j++){
                    N = rowswap(E,i,j);
                    if(hash[N] == -1){
                        hash[N] = hash[E]+1;
                        ans[N] = E;
                        Q.add(N);
                    }
                }
            for(int i=0; i<n-1; i++)
                for(int j=i+1; j<n; j++){
                    N = colswap(E,i,j);
                    if(hash[N] == -1){
                        hash[N] = hash[E]+1;
                        ans[N] = E;
                        Q.add(N);
                    }
                }

            for(int i=0; i<m; i++){
                N = revrow(E,i);
                if(hash[N] == -1){
                    hash[N] = hash[E]+1;
                    ans[N] = E;
                    Q.add(N);
                }
            }
            for(int i=0; i<n; i++){
                N = revcol(E,i);
                if(hash[N] == -1){
                    hash[N] = hash[E]+1;
                    ans[N] = E;
                    Q.add(N);
                }
            }
            if(hash[dest] != -1) return true;
        }

        return false;
    }

    private static int rowswap(int x, int i, int j){
        rowx(x);
        swap(row,i,j);
        X = x;
        rowy();
        x = X;

        return x;
    }

    private static int colswap(int x, int i, int j){
        colx(x);
        swap(col,i,j);
        X = x;
        coly();
        x = X;

        return x;
    }

    private static void swap(int[] x, int i, int j){
        int tmp = x[i];
        x[i] = x[j];
        x[j] = tmp;
    }

    private static int revrow(int x, int i){
        rowx(x);

//        reve(row[i],n);
        reve(row,i,n);

        X = x;
        rowy();
        x = X;
//        rowy(x);

        return x;
    }

    private static int revcol(int x, int i){
        colx(x);

        reve(col,i,m);
//        reve(col[i],m);

        X = x;
        coly();
        x = X;
//        coly(x);

        return x;
    }

    private static void rowx(int x){
        for(int i=0; i<m; i++){
            int y=1; row[i]=0;
            for(int j=0; j<n; j++){
                if((x&1) > 0) row[i]|=y;
                y<<=1; x>>=1;
            }
        }
    }

    private static void colx(int x){
        for(int j=0; j<n; j++) col[j]=0;
        int y=1;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if((x&1) > 0) col[j]|=y;
                x>>=1;
            }
            y<<=1;
        }
    }

    private static void rowy(){
        X=0;
        for(int i=0,z=1; i<m; i++){
            int y = row[i];
            for(int j=0; j<n; j++){
                if((y&1) > 0) X|=z;
                z<<=1; y>>=1;
            }
        }
    }

    private static void coly(){
        X=0;
        for(int i=0,z=1; i<m; i++){
            for(int j=0; j<n; j++){
                if((col[j]&1) > 0) X|=z;
                col[j]>>=1; z<<=1;
            }
        }
    }

    private static void reve(int[] x, int i, int k){
        int y=0,z=1<<(k-1);
        for(int j=0; j<k; j++){
            if((x[i]&1) > 0) y|=z;
            z>>=1; x[i]>>=1;
        }
        x[i] = y;
    }

    private static void output(int N){
        if(N == sour){
            System.out.println();
            outb(N);
            return;
        }
        output(ans[N]);
        System.out.println();
        outb(N);
    }

    private static void outb(int x){
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if((x&1) > 0) System.out.print(1);
                else System.out.print(0);
                x /= 2;
            }
            System.out.println();
        }
    }
}