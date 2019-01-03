package com.bgi.correct;

import java.util.Arrays;
import java.util.List;

import opennlp.tools.ngram.NGramGenerator;

public class NGram {

	/**
	 * 句子统计
	 * @param sentence
	 * @param n
	 * @param separator
	 * @return
	 */
	public static List<String> split(List<String> source, int n, String separator) {
		return NGramGenerator.generate(source, n, separator);
	}
	
	
	/**
	 * 英文字符默认按照空格切分
	 * @param source
	 * @param n
	 * @param separator
	 * @return
	 */
	public static List<String> process(String source, int n, String separator) {
		String[] array = source.toLowerCase().split("\\s+");
		return split(Arrays.asList(array), n, separator);
	}
	
	
	/**
	 * 中文句子统计
	 * @param sentence
	 * @param n
	 * @param separator
	 * @return
	 */
	public static List<String> split(String sentence, int n, String separator) {
		char[] chars = sentence.toCharArray();
		return NGramGenerator.generate(chars, n, separator);
	}

	
	public static void main(String[] args) {
		String sentence = "我爱自然语言处理哦";
		List<String> ret = split(sentence, 3, "");
		System.out.println(ret);
	}

}
