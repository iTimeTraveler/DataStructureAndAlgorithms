package algorithms;

import java.util.ArrayList;

/**
 * 最长公共子序列 Longest Common Subsequences
 */
public class LongestCommonSubsquence {
	private static final String DIRCTOR_LEFT_UP = "↖";
	private static final String DIRCTOR_LEFT = "←";
	private static final String DIRCTOR_UP = "↑";

	/**
	 * @param args
	 */
	public static void main(String args[]){
		String a = "acgbfhk";
		String b = "cegefkh";

		String str1 = "abcbdab";
		String str2 = "bdcaba";
		LongestCommonSubsquence.LCSLength(a, b);
	}

	/**
	 * 动态规划算法解LCS问题
	 *
	 * 请编写一个函数，输入两个字符串，求它们的最长公共子序列，并打印出最长公共子序列。
	 * 例如：输入两个字符串BDCABA和ABCBDAB，字符串BCBA和BDAB都是是它们的最长公共子序列，则输出它们的长度4，并打印任意一个子序列。
	 */
	public static String LCSLength(String a, String b){
		if(a == null || b == null || a.isEmpty() || b.isEmpty()){
			return null;
		}
		ArrayList<Character> list = new ArrayList<Character>();
		int[][] matrix = new int[a.length() + 1][b.length() + 1];
		String[][] arrow = new String[a.length() + 1][b.length() + 1];

		//初始化边界，过滤掉0的情况
		for(int i = 1; i < a.length() + 1; i++){
			matrix[i][0] = 0;
			arrow[i][0] = String.valueOf(a.charAt(i-1));
		}
		for(int j = 1; j < b.length() + 1; j++){
			matrix[0][j] = 0;
			arrow[0][j] = String.valueOf(b.charAt(j-1));
		}

		//填充矩阵
		for(int i = 1; i < a.length() + 1; i++) {
			for (int j = 1; j < b.length() + 1; j++) {

				//相等的情况
				if(a.charAt(i - 1) == b.charAt(j - 1)){
					matrix[i][j] = matrix[i-1][j-1] + 1;
					arrow[i][j] = DIRCTOR_LEFT_UP;
					list.add(a.charAt(i - 1));
				}else{
					//比较“左边”和“上边”，根据其max来填充
					matrix[i][j] = Math.max(matrix[i][j-1], matrix[i-1][j]);
					arrow[i][j] = (matrix[i][j-1] >= matrix[i-1][j]) ? DIRCTOR_LEFT : DIRCTOR_UP;
				}
			}
		}

		//打印结果矩阵
		for(int i = 0; i < a.length() + 1; i++){
			for(int j = 0; j < b.length() + 1; j++){
				System.out.print(arrow[i][j] + "" + matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.print(list);
		LCSSting(arrow, arrow.length - 1, arrow[0].length - 1, a);
		return null;
	}

	/**
	 * 输出最长公共子序列内容
	 * @param arrow
	 * @param i
	 * @param j
	 * @param src
	 */
	private static void LCSSting(String[][] arrow, int i, int j, String src){
		if (i == 0 || j == 0) return;

		if(DIRCTOR_LEFT_UP.equals(arrow[i][j])){
			System.out.print(src.charAt(i-1));
			LCSSting(arrow, i - 1, j - 1, src);
		}else{
			if(DIRCTOR_LEFT.equals(arrow[i][j])){
				LCSSting(arrow, i, j - 1, src);
			}else{
				LCSSting(arrow, i - 1, j, src);
			}
		}
	}
}
