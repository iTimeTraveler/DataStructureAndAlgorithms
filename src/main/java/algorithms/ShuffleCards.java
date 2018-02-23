package algorithms;
// Author : iTimeTraveler
// Date   : 2018-02-23

import java.util.Arrays;
import java.util.Random;

/**
 * 洗牌算法
 */
public class ShuffleCards {

	public static void main(String args[]){
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		fisherYatesShuffle(array);
		System.out.println(Arrays.toString(array));
		test();
	}

	public static void fisherYatesShuffle(int[] arr){
		if(arr == null || arr.length == 0) return;

		for (int i = arr.length - 1; i > 0; i--){
			Random r = new Random();
			int index = r.nextInt(i + 1);

			//swap
			int temp = arr[index];
			arr[index] = arr[i];
			arr[i] = temp;
		}
	}


	private static void test(){
		int[][] result = new int[10][10];

		for (int i = 0; i < 1000000; i++){
			int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			fisherYatesShuffle(array);

			for (int j = 0; j < array.length; j++){
				int num = array[j];
				result[j][num - 1] += 1;
			}
		}

		System.out.println("----------------------------------------------------");
		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
		}
	}
}
