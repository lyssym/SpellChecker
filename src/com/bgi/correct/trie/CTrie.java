package com.bgi.correct.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.bgi.io.IOUtil;
import com.bgi.bktree.trie.BNode;
import com.bgi.bktree.trie.Trie;
import com.bgi.correct.NGram;
import com.bgi.util.SystemConfig;

/***
 * N-Gram统计后构建的键树
 * @author liuyong
 *
 */
public class CTrie extends Trie {
	public static int THRESHOLD = SystemConfig.THRESHOLD;
	public static int N = SystemConfig.N_GRAM;
	public static String SEPARATOR = SystemConfig.SEPARATOR;

	
	/**
	 * 构建NGram语言模型
	 */
	public void initTrie(String fileName) {
		try {
			BufferedReader br = IOUtil.loadResource(fileName, 2);
			String s = null;
			while ((s = br.readLine()) != null) {
				List<String> arr = NGram.process(s, N, SEPARATOR);
				for (String e : arr) {
					addKey(e);
				}
				
				arr = NGram.process(s, N-1, SEPARATOR);
				for (String e : arr) {
					addKey(e);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    /***
     * 插入节点, 对于相同的字符串，累计计算频率
     * @param word
     */
    public void addKey(String word) {
        BNode node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            BNode subNode = (BNode)node.values.get(c);
            if (subNode == null) {
                subNode = new BNode();
                node.values.put(c, subNode);
            }
            node = subNode;
        }

        node.end = true;
        node.name = word;
        node.hot += 1;
    }
    
    
    /**
     * 检索词汇, 并返回热度信息
     * @param word
     * @return 
     */
    public double searchKey(String word) {
        if (word == null || "".equals(word)) {
            return -1;
        }
        
        BNode node = root;
        int size = word.length();
        BNode subNode = null;
        for (int i = 0; i < size; i++) {
            char c = word.charAt(i);
            subNode = (BNode)node.values.get(c);
            if (subNode == null) { // 节点不存在
                return -1;
            }
            node = subNode;
        }

        if (node.end && node.hot > THRESHOLD) { // 节点匹配命中，且频度超过某阈值
        	return node.hot;
        } else {                                // 节点不存在
        	return -1;
        }
    }
}
