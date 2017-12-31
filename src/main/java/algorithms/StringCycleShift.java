package algorithms;


/**
 * 写一个算法，对于给定的两个字符串A，B，判断B是否可以被A循环移位后包含。
 * 比如A = bcdaa，B = aabc，对字符串A进行循环右移两个字符得到新字符串aabcd，包含B
 */
public class StringCycleShift {

	public static void main(String args[]){
		String a = "bcqvhugbaa";
		String b = "aabc";
		System.out.println(isContainsByConcat(a, b));
		System.out.println(isContainsByPointer(a, b));
	}

	/**
	 * 解法二：拼接字符串s1+s1
	 * 如果s2可以由s1循环移位得到，那么s2一定在s1s1上
	 */
	public static boolean isContainsByConcat(String a, String b){
		if(a == null || b == null || a.isEmpty() || b.isEmpty()) return false;

		String newStr = a.concat(a);
		return newStr.contains(b);
	}

	/**
	 * 解法三：指针循环查找
	 */
	public static boolean isContainsByPointer(String a, String b){
		if(a == null || b == null || a.isEmpty() || b.isEmpty()) return false;

		int p = 0;	//s1的指针

		int aLen = a.length();
		int bLen = b.length();
		for(int i = 0; i < aLen * 2; i++){
			char c1 = a.charAt(p % aLen);

			//匹配到第一个字符相同后，遍历s2字符串
			if(c1 == b.charAt(0)){
				for(int j = 0; j < bLen; j++){
					if(a.charAt((p + j) % aLen) != b.charAt(j)){
						break;	//本次不完全匹配，继续往后
					}
				}
				return true;
			}
			p++;
		}
		return false;
	}
}
