package algorithms;

/**
 * 回文字符串
 */
public class PalindromeString {

	public static void main(String args[]){
		System.out.println(isPalindromeStringByAddChar("abc"));
		System.out.println(isPalindromeStringByAddChar("aba"));
		System.out.println(isPalindromeStringByAddChar("abac"));
		System.out.println(isPalindromeStringByAddChar("abccb"));
		System.out.println(isPalindromeStringByAddChar("abceba"));

		System.out.println(isPalindromeStringByAddOneChar("abc"));
		System.out.println(isPalindromeStringByAddOneChar("aba"));
		System.out.println(isPalindromeStringByAddOneChar("abac"));
		System.out.println(isPalindromeStringByAddOneChar("abccb"));
		System.out.println(isPalindromeStringByAddOneChar("abceba"));
	}

	/**
	 * 判断一个String是否可以通过添加一个字符构成回文字符串
	 */
	public static boolean isPalindromeStringByAddOneChar(String src){
		if(src == null || src.length() == 0) return false;

		String reverse = new StringBuilder(src).reverse().toString();
		char[] originalChars = src.toCharArray();
		char[] reverseChars = reverse.toCharArray();

		//反转的String和src逐位比较
		for (int i = 0; i < originalChars.length; i++){
			String result;

			//把不同的位插入到新的位置后，再判断是不是构成了回文串
			if(originalChars[i] != reverseChars[i]){

				//有两种情况，反转串中的特殊字符插入到src中
				result = new StringBuilder(src).insert(i, reverseChars[i]).toString();
				if(result.equals(new StringBuilder(result).reverse().toString())){
					return true;
				}

				//或者src串中的特殊字符插入到反转串中
				result = new StringBuilder(reverse).insert(i, originalChars[i]).toString();
				if(result.equals(new StringBuilder(result).reverse().toString())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断一个String是否可以通过添加一个字符构成回文字符串
	 *
	 * 动态规划算法：
	 * 		利用源字符串和翻转后的字符串，求最长公共子序列。长度 - 公共子序列的长度 = 添加字符的个数
	 */
	public static boolean isPalindromeStringByAddChar(String src){
		if(src == null || src.length() == 0) return false;

		String reverse = new StringBuilder(src).reverse().toString();
		char[] originalChars = src.toCharArray();
		char[] reverseChars = reverse.toCharArray();

		int[][] matrix = new int[originalChars.length + 1][originalChars.length + 1];
		for (int i = 0; i < originalChars.length; i++) {
			matrix[i][0] = 0;
		}

		for (int i = 0; i < originalChars.length; i++) {
			matrix[0][i] = 0;
		}

		for (int j = 1; j <= originalChars.length; j++) {
			for (int k = 1; k <= reverseChars.length; k++) {
				//相等的情况
				if(originalChars[j-1] == reverseChars[k-1]){
					matrix[j][k] = matrix[j-1][k-1] + 1;
				}else{
					//比较“左边”和“上边”，根据其max来填充
					matrix[j][k] = Math.max(matrix[j][k-1], matrix[j-1][k]);
				}
			}
		}

		return (src.length() - matrix[originalChars.length][originalChars.length]) == 1;
	}
}
