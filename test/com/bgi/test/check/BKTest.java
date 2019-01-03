package com.bgi.test.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bgi.bktree.BKTree;
import com.bgi.bktree.LevensteinDistance;
import com.bgi.bktree.MetricSpace;
import com.bgi.io.IOUtil;
import com.bgi.trie.Trie;

public class BKTest {
	
	/**
	 * 插入节点信息
	 * @param head
	 * @param synonym
	 * @param category
	 */
	public static List<String> initTree(String fileName) {
		List<String> collects = new ArrayList<String>();
		try {
			BufferedReader br = IOUtil.loadResource(fileName, 2);
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] arr = s.split("\t");
				collects.add(arr[0].trim());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return collects;
	}

	
	public static void main(String[] args) {
        double radius = 3;          // 编辑距离阈值
        String term = "lik";        // 待纠错的词
        String fileName = "data.txt";
        Trie trie = new Trie();
        trie.initTrie(fileName);
        
        // 创建BK树
        MetricSpace<String> ms = new LevensteinDistance();
        List<String> elems = initTree(fileName);
        BKTree<String> bk = BKTree.mkBKTree(ms, trie, elems);
        
        List<String> set = bk.query(term, radius);
        System.out.println(set.toString());

	}

}
