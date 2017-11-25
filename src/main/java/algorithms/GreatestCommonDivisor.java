package main.java.algorithms;

public class GreatestCommonDivisor {
	
	public static void main(String args[]){
		System.out.println("" + GreatestCommonDivisor.gcd(9, 6));
	}

	/**
	 * 辗转相除法：两个正整数a和b（a>b），它们的最大公约数等于a除以b的余数c和b之间的最大公约数。
	 */
	public static int gcd(int a, int b){
		int c = 1;
		if(a < b){
			a = a ^ b;
			b = a ^ b;
			a = a ^ b;
		}
		
		while(c != 0){
			c = a % b;
			a = b;
			b = c;
		}
		return a;			
	}
}
