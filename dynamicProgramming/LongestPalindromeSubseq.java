package dynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class LongestPalindromeSubseq {
	public static void main(String[] args){
		System.out.println(longestPalindromeSubseq("scharacters"));
		System.out.println(longestPalindromeSubseq("abcdefghijklmnopqrstuvwxyzzzzzzzzzpzzzzzzzzzzzzzzzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzzzzzzzzzzzzzzzzzzzzzzzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzzzzzzzzzzzzzzzzzzzzzzzzyxwvutsrqponmlkjihgfedcba"));

		Random r = new Random();
		Map<Integer, Double> results = new TreeMap<>();

		for(int len = 0; len <= 5000; len += 100){
			// 33 ~ 126 inclusive -> any char between ! and ~
			StringBuilder s = new StringBuilder(len);
			for(int j = 0; j < len; j++)
				s.append((char) (r.nextInt(127 - 33) + 33));
			String str = s.toString();

			long start = System.currentTimeMillis();
			String pal = longestPalindromeSubseq(str);
			double time = (System.currentTimeMillis() - start)/1000.;
			System.out.println("Length " + len + ": " + str + " -> " + pal + " took " + time + " secs.");
			results.put(len, time);
		}

		for(Integer key : results.keySet()){
			System.out.print(key + ", ");
		}
		System.out.println();
		for(Integer key : results.keySet()){
			System.out.print(results.get(key) + ", ");
		}
	}

	private static String longestPalindromeSubseq(String str) {
		if(str.length() == 0)
			return "";

		Map<Integer, Map<Integer, String>> memo = new HashMap<>(); // memo.get(i).get(j) has longest palindrome from idx [i,j) in seq
		for(int i = 0; i < str.length(); i++){
			Map<Integer, String> m = new HashMap<>(str.length() - i);
			m.put(i, "");
			m.put(i + 1, str.substring(i, i + 1));
			memo.put(i, m);
		}

		for(int len = 2; len <= str.length(); len++){
			for(int s = 0; s <= str.length() - len; s++){
				int e = s + len;
				String next;
				if(str.charAt(s) == str.charAt(e-1))
					next = str.substring(s, s + 1) + memo.get(s + 1).get(e - 1) + str.substring(e - 1, e);
				else if(memo.get(s + 1).get(e).length() > memo.get(s).get(e - 1).length())
					next = memo.get(s + 1).get(e);
				else
					next = memo.get(s).get(e - 1);
				memo.get(s).put(e, next);
			}
		}

		//		for(int i = 0; i < str.length(); i++)
		//			for(int j = i; j < str.length(); j++)
		//				if(memo.containsKey(i) && memo.get(i).containsKey(j))
		//					System.out.println("("+i+","+j+"): "+memo.get(i).get(j));
		return memo.get(0).get(str.length());
	}




	private static String naiveLongestPalindromeSubseq(String seq) {
		return naiveLongestPalindromeSubseq(seq.toCharArray());
	}

	private static String naiveLongestPalindromeSubseq(char[] seq) {
		Map<Integer, Map<Integer, String>> memo = new HashMap<>(); // memo.get(i).get(j) has longest palindrome from idx [i,j) in seq
		String longest = naiveLongestPalindromeSubseq(seq, memo, 0, seq.length);
		//		for(int i = 0; i < seq.length; i++)
		//			for(int j = i; j < seq.length; j++)
		//				if(memo.containsKey(i) && memo.get(i).containsKey(j))
		//					System.out.println("("+i+","+j+"): "+memo.get(i).get(j));
		return longest;
	}

	private static String naiveLongestPalindromeSubseq(char[] seq, Map<Integer, Map<Integer, String>> memo, int i, int j) {
		if(j < i)
			throw new IllegalArgumentException("i (" + i + ") must be greater than or equal to j (" + j + ").");
		if(i + 1 >= j){
			return new String(Arrays.copyOfRange(seq, i, j));
		}
		if(memo.containsKey(i) && memo.get(i).containsKey(j)){
			return memo.get(i).get(j);
		}

		String longest = "";
		for(int s = i; s < j; s++){
			int e = j - 1;
			for(; e > s; e--)
				if(seq[s] == seq[e])
					break;

			if(s != e){
				// add to memo
				if(!memo.containsKey(s))
					memo.put(s, new HashMap<>());
				String localLong = seq[s] + naiveLongestPalindromeSubseq(seq, memo, s + 1, e) + seq[e];
				memo.get(s).put(e + 1, localLong);
				if(localLong.length() > longest.length())
					longest = localLong;
			}
		}

		return longest;
	}
}
