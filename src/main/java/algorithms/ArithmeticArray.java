package algorithms;

/**
 * 有这样一个数组A，大小为n，相邻元素差的绝对值都是1，如A={4,5,6,5,6,7,8,9,10,9}。
 * 现在给定数组A和目标整数t，请找到t在数组中的位置。
 */
public class ArithmeticArray {

	public static void main(String args[]){
		int[] arr = {4,5,6,5,6,7,8,9,10,9};
		System.out.println(findTargetFromArithmeticArray(arr, 8));
	}

	/**
	 * 跨越式搜索
	 */
	public static int findTargetFromArithmeticArray(int[] array, int target){
		if(array == null || array.length == 0) return -1;

		int i = 0;
		while(i >= 0 && i < array.length){
			int gap = Math.abs(target - array[i]);	//当前元素与目标的差值

			i = i + gap;
			if(array[i] == target){
				return i;
			}
		}
		return -1;
	}
}
