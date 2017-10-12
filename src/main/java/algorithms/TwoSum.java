package algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
	private static final int ARRAY_LENGTH = 10000000;
	private static final int MAX_VALUE = 9000;

	public static void main(String args[]){

//		int[] arr = new int[]{9, 8, 7, 6, 5, 4, 3, 12, 11};
		int[] arr = new int[ARRAY_LENGTH];
		for(int i = 0; i < ARRAY_LENGTH; i++){
			arr[i] = (int)(Math.random() * MAX_VALUE);
		}
		System.out.println(Arrays.toString(arr));
		System.out.println(Integer.BYTES * arr.length + "byte");
		int[] result = findTwoIntSumEqualsN(arr, 1005);
		System.out.println("result: " + Arrays.toString(result));
	}

	/**
	 * 数组A由1000万个随机正整数(int)组成，设计算法，给定整数n，在数组A中找出 a 和 b，使其符合：n = a + b
	 */
	public static int[] findTwoIntSumEqualsN(int[] arr, int n){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for(int i = 0; i < arr.length; i++){
			int value = arr[i];
			if(value > n) continue;

			int complement = n - value;
			if(map.containsKey(complement)){
				System.out.println("Two indexes are: " + i + "," + map.get(complement));
				return new int[]{value, complement};
			}
			map.put(value, i);
		}
		throw new IllegalArgumentException("No two sum solution");
	}
}
