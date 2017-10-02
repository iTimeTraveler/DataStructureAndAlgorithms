package main.java.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Median {
	
	public static void main(String args[]){
		Integer arr[] = new Integer[]{9, 8, 7, 6, 5, 4, 3, 12, 11};
//		Integer arr[] = new Integer[]{1,2,3,4,5,6};
		
		Comparator<Integer> cmpLarger = new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		};
		List<Integer> res = findMedianFromRandomArray(arr, cmpLarger);
		System.out.println(Arrays.asList(res));
	}
	
	/**
	 * 给定一个int数组A，为传入的数字序列，同时给定序列大小n，请返回一个int数组，代表每次传入后的中位数。保证n小于等于1000
	 * 
	 * 1. 通过最大堆、最小堆来实现实时中位数的获取。
	 * 2. 最大堆中存放比最小堆小的元素。
	 * 3. 如果最大堆的对头元素大于最小堆，则进行交换。
	 * 4. 偶数下标的元素存入最小堆，奇数下标的元素存入最大堆。
	 * 
	 * @param arr
	 * @param cmp
	 */
	public static <E> List<E> findMedianFromRandomArray(E[] arr, Comparator cmp){
		List<E> res = new ArrayList<E>(arr.length);
		PriorityQueue<E> maxHeap = new PriorityQueue<E>(cmp);
		PriorityQueue<E> minHeap = new PriorityQueue<E>();
		
		for(int i = 0; i < arr.length; i++){
			if(i % 2 == 0){
				// 存入最小堆前判断当前元素是否小于最大堆的堆顶元素
				if(!maxHeap.isEmpty() && cmp.compare(arr[i], maxHeap.peek()) > 0){
					minHeap.offer(maxHeap.poll());
					maxHeap.offer(arr[i]);
				}else{
					minHeap.offer(arr[i]);
				}
				res.add(minHeap.peek());
			}else{
				// 存入最大堆前判断当前元素是否大于最小堆的堆顶元素
				if(!minHeap.isEmpty() && cmp.compare(minHeap.peek(),arr[i]) > 0){
					maxHeap.offer(minHeap.poll());
					minHeap.offer(arr[i]);
				}else{
					maxHeap.offer(arr[i]);
				}
				res.add(maxHeap.peek());
			}
		}
		return res;
	}

}
