package algorithms;

public class BinarySearch {

	public static void main(String args[]){
//		int array[] = new int[]{2,4,5,7,8,9,13,23,34,45};
		int array[] = new int[]{2,2,2,2,2,2,4,4,4,5,5};

		System.out.println(binarySearch(array, 4));
		System.out.println(binarySearchRecrusion(array, 4, 0, array.length - 1));
		System.out.println(findFirstIndex(array, 4));
		System.out.println(findLastIndex(array, 4));
	}

	/**
	 * 二分查找（迭代实现）
	 */
	public static int binarySearch(int[] arr, int target){
		if(arr == null || arr.length < 1) return -1;

		int low = 0;
		int high = arr.length - 1;
		while(low < high){
			int middle = (low + high) / 2;
			if(target == arr[middle]) return middle;

		 	if(target > arr[middle]){
				low = middle + 1;
			}else if(target < arr[middle]){
				high = middle - 1;
			}
		}
		return -1;
	}

	/**
	 * 二分查找（递归实现）
	 */
	public static int binarySearchRecrusion(int[] arr, int target, int start, int end){
		if(arr == null || arr.length < 1) return -1;
		if(start > end) return -1;

		int middle = (start + end) >> 1;
		if(target == arr[middle]){
			return middle;
		}
		if(target > arr[middle]){
			start = middle + 1;
		}else if(target < arr[middle]){
			end = middle - 1;
		}
		return binarySearchRecrusion(arr, target, start, end);
	}

	/**
	 * 二分查找实现查找元素出现的第一个位置
	 */
	public static int findFirstIndex(int[] arr, int target){
		if(arr == null || arr.length < 1) return -1;

		int low = 0;
		int high = arr.length;
		int result = -1;
		while(low <= high){
			int middle = (low + high) >> 1;
			if(target == arr[middle]){
				result = middle;
			}

			if(target <= arr[middle]){
				high = middle - 1;
			}else if(target >= arr[middle]){
				low = middle + 1;
			}
		}
		return result;
	}

	/**
	 * 二分查找实现查找元素出现的最后一个位置
	 */
	public static int findLastIndex(int[] arr, int target){
		if(arr == null || arr.length < 1) return -1;

		int low = 0;
		int high = arr.length;
		int result = -1;
		while(low <= high){
			int middle = (low + high) >> 1;
			if(target == arr[middle]){
				result = middle;
			}

			if(target >= arr[middle]){
				low = middle + 1;
			}else if(target <= arr[middle]){
				high = middle - 1;
			}
		}
		return result;
	}
}
