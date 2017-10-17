package algorithms;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;

public class CharacterSubset {
	public static int primes[] =  {
			2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,
			107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,
			223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,
			337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,
			457,461,463,467,479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,
			593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,
			719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,853,
			857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991};
	// 字母编码[A - z]:[65 - 122]
	public static final int LETTER_REGION = 122 - 65 + 1;


	/**
	 * 假设你有一个一定长度的由字母组成的字符串。你还有另外一个，短些。你如何才能知道所有的在较短的字符串里的字母在长字符串里也有？
	 */
	public static void main(String args[]){
		String a1 = "ABCDEFGHLMNOPQRS";
		String b1 = "DCGSRQPOM";

		String a2 = "ABCDEFGHLMNOPQRS";
		String b2 = "DCGSRQPOZ";

		System.out.println("\na1 and b1: " + isSubsetByHashset(a1, b1));
		System.out.println("\na2 and b2: " + isSubsetByHashset(a2, b2));
		System.out.println("\na1 and b1: " + isSubsetByPrimeNumber(a1, b1));
		System.out.println("\na2 and b2: " + isSubsetByPrimeNumber(a2, b2));
		System.out.println("\na1 and b1: " + isSubsetByBitmap(a1, b1));
		System.out.println("\na2 and b2: " + isSubsetByBitmap(a2, b2));
	}

	/**
	 * 哈希表Hashset
	 */
	public static boolean isSubsetByHashset(String a, String b){
		char[] ca = a.toCharArray();
		char[] cb = b.toCharArray();
		HashSet<Character> set = new HashSet<Character>();

		for(char c : ca){
			set.add(c);
		}
		for(char c : cb){
			if(!set.contains(c)){
				return false;
			}
		}
		return true;
	}

	/**
	 * 素数方案
	 */
	public static boolean isSubsetByPrimeNumber(String a, String b){
		char[] ca = a.toCharArray();
		char[] cb = b.toCharArray();
		primes = generatePrimes(LETTER_REGION);

		// 防止乘积int溢出，使用BigInteger存储乘积结果
		BigInteger p = BigInteger.ONE;
		for(char c : ca){
			p = p.multiply(BigInteger.valueOf(primes[c - 'A']));
		}
		System.out.println("乘积结果p = " + p.toString());
		for(char c : cb){
			if(!p.remainder(BigInteger.valueOf(primes[c - 'A'])).equals(BigInteger.ZERO)){
				System.out.println("No exist char: " + c);
				return false;
			}
		}
		return true;
	}

	/**
	 * 比特位方案
	 */
	public static boolean isSubsetByBitmap(String a, String b){
		char[] ca = a.toCharArray();
		char[] cb = b.toCharArray();
		byte[] bitmap = new byte[LETTER_REGION / Byte.SIZE];

		for(char c : ca){
			TwoSum.setBit(bitmap, c - 'A');
		}
		for(char c : cb){
			if(TwoSum.getBit(bitmap, c - 'A') == 0){
				System.out.println("No exist char in Bitmap: " + c);
				return false;
			}
		}
		return true;
	}


	/**
	 * 生成质数算法
	 * @param len
	 * @return
	 */
	public static int[] generatePrimes(int len){
		int[] result = new int[len];
		int x = 0;

		for(int i = 0; i < len; ){
			if(isPrime(x)){
				result[i++] = x;
			}
			x++;
		}
		System.out.println("质数数组：" + Arrays.toString(result));
		return result;
	}

	private static boolean isPrime(int x){
		if(x < 2) return false;
		if(x == 2) return true;
		if(x % 2 == 0) return false;

		for(int i = 3; i < x; i++){
			if(x % i == 0) return false;
		}
		return true;
	}
}
