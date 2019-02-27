package homework.test;

import java.util.Scanner;

public class Main {
	public static boolean Travel(int firstX, int firstY, int[][] maps) {

		// 8个方向
		int[] nextx = { -2, -1, 1, 2, 2, 1, -1, -2 };
		int[] nexty = { 1, 2, 2, 1, -1, -2, -2, -1 };

		// 下一步出路的位置
		int[] nextStepX = new int[maps.length];
		int[] nextStepY = new int[maps.length];


		int xx = firstX;
		int yy = firstY;
		maps[xx][yy] = 1;

		for (int m = 2; m <= Math.pow(maps.length, 2); m++) {

			// 记录出路的个数
			int[] exitS = new int[maps.length];

			int cnt = 0;
			// 试探8个方向
			for (int i = 0; i < 8; i++) {
				int temX = xx + nextx[i];
				int temY = yy + nexty[i];

				// 走到边界
				if (temX < 0 || temX > 7 || temY < 0 || temY > 7) {
					continue;
				}

				// 记录下可走的方向
				if (0 == maps[temX][temY]) {
					nextStepX[cnt] = temX;
					nextStepY[cnt] = temY;
					cnt++;
				}
			}

			int min = -1;
			if (cnt == 0) {
				return false;
			}

			if (1 == cnt) {
				min = 0;
			} else {// 预先判断，剪枝
				for (int i = 0; i < cnt; i++) {
					for (int j = 0; j < 8; j++) {
						int temX = nextStepX[i] + nextx[j];
						int temY = nextStepY[i] + nexty[j];
						if (temX < 0 || temX > 7 || temY < 0 || temY > 7) {
							continue;
						}

						// 记录下这个位置可走的方向数
						if (0 == maps[temX][temY]) {
							exitS[i]++;
						}
					}
				}

				int tem = exitS[0];
				min = 0;

				for (int i = 1; i < cnt; i++) {
					if (tem > exitS[i]) {
						tem = exitS[i];
						min = i;
					}
				}
			}

			xx = nextStepX[min];
			yy = nextStepY[min];
			maps[xx][yy] = m;

			printMatrix(maps);
		}

		return true;
	}

	public static void main(String[] args) {

		int firstX, firstY;
//		System.out.println("input start point: ");
//		Scanner scanner = new Scanner(System.in);

//		firstX = scanner.nextInt();
//		firstY = scanner.nextInt();

		firstX = 3;
		firstY = 3;
		int[][] maps = new int[8][8];

		if (Travel(firstX, firstY, maps)) {
			System.out.println("Travel success：");
			for (int i = 0; i < maps.length; i++) {
				for (int j = 0; j < maps[0].length; j++) {
					if (maps[i][j] < 10) {
						System.out.print(" " + maps[i][j]);
					} else {
						System.out.print(maps[i][j]);
					}
					System.out.print(" ");
				}
				System.out.println();
			}
		} else {
			System.out.println("Travel failure!\n");
		}
		
//		scanner.close();
	}

	public static void printMatrix(int maps[][]){

		for(int i = 0; i < maps.length; i++){
		   for(int j = 0; j < maps[0].length; j++){
			   System.out.print(maps[i][j] + " ");
		   }
			System.out.println();
		}
		System.out.println("-------------------------");
	}
}