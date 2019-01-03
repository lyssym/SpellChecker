package com.bgi.correct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bgi.bktree.BKTree;
import com.bgi.bktree.Element;
import com.bgi.correct.trie.CTrie;


public class Correct {
	public static int N = 3;
	public static String SEPARATOR = " ";
	public static CTrie TRIE = null;
	
	public static double RADIUS = 1;
	public static BKTree<String> BKTREE = null;
	
	
	public static String extract(String text, int N) {
		String[] arr = text.split(SEPARATOR);
		return arr[N - 1];
	}
	
	
	public static String collect(String text, int N) {
		String[] arr = text.split(SEPARATOR);
		
		StringBuilder sb = new StringBuilder();
		for (int i = N - 2; i < N; i++) {
			if (i == N - 1) {
				sb.append(arr[i]);
			} else {
				sb.append(arr[i] + SEPARATOR);
			}
		}
		
		return sb.toString();
	}


	public static String correct(String sentence) {
		List<String> source = NGram.process(sentence, N, SEPARATOR);
		List<String> target = NGram.process(sentence, N - 1, SEPARATOR);
		
		StringBuilder sb = new StringBuilder();
		boolean pre = false;
		boolean cur = false;

		int size = source.size();
		for (int i = 0; i < size; i++) {
			pre = cur;
			String word = source.get(i);
			double hot = TRIE.searchKey(word);
			
			if (hot == -1) {
				word = target.get(i);
				hot = TRIE.searchKey(word);
				
				if (hot > 0) {
					cur = false;
					if (i == 0) {
						sb.append(word + SEPARATOR);
					} else {
						if (pre == cur) {
							sb.append(extract(word, N - 1) + SEPARATOR);
						}
					}
					continue;
				}
			}
			
	        cur = true;  // 即使未能匹配，也走该路径
		    if (i == 0) {
				sb.append(word + SEPARATOR);
			} else {
				if (pre == cur) {
					sb.append(extract(word, N) + SEPARATOR);
				} else {
					sb.append(collect(word, N) + SEPARATOR);
				}
			}
		}
		
		return sb.toString();
	}
	
	
	public static String create(String[] arr, int index, String word) {
		StringBuilder sb = new StringBuilder();
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			if (i == index) {
				if (i == len) {
					sb.append(word);
				} else {
					sb.append(word + SEPARATOR);
				}
			} else {
				if (i == len) {
					sb.append(arr[i]);
				} else {
					sb.append(arr[i] + SEPARATOR);
				}
			}
		}
		
		return sb.toString();
	}
	
	
	private static void sort(List<Element<String>> result) {
		Collections.sort(result, new Comparator<Element<String>>() {
			@Override
            public int compare(Element<String> src, Element<String> dst) {
                if (src.hot >= dst.hot) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
	}
	
	
	public static String correctDistance(String sentence) {
		List<String> source = NGram.process(sentence, N, SEPARATOR);
		
		StringBuilder sb = new StringBuilder();
		int size = source.size();
		for (int i = 0; i < size; i++) {
			String word = source.get(i);
			double hot = TRIE.searchKey(word);
			
			if (hot == -1) {
				String[] arr = word.split(SEPARATOR);
				List<Element<String>> ret = new ArrayList<Element<String>>();
				for (int j = 0; j < arr.length; j++) {
					List<Element<String>> tmp = BKTREE.search(arr[i], RADIUS);
					if (tmp != null && tmp.size() > 0) {
						for (int k = 0; k < tmp.size(); k++) {
							String data = tmp.get(k).data;
							data = create(arr, j, data);
							hot = TRIE.searchKey(data);
							if (hot > 0) {
								ret.add(tmp.get(k));
							}
						}	
					}
				}
				
				if (ret.size() > 0) {
					sort(ret);
					word = ret.get(0).data;
				}
			}
			
			if (i == 0) {
				sb.append(word + SEPARATOR);
			} else {
				if (i == size - 1) {
					sb.append(extract(word, N));
				} else {
					sb.append(extract(word, N) + SEPARATOR);
				}
			}
		}
		
		return sb.toString();
	}


	public static void main(String[] args) {
		String text = "I Love NLP hello world";
		System.out.println(extract(text, 3));

	}

}
