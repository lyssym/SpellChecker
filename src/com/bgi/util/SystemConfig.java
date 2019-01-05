package com.bgi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.bgi.bktree.BKTree;
import com.bgi.bktree.LevensteinDistance;
import com.bgi.bktree.MetricSpace;
import com.bgi.correct.trie.CTrie;
import com.bgi.io.IOUtil;
import com.bgi.bktree.trie.Trie;
import com.bgi.suggester.trie.PTrie;

/**
 * 配置信息
 * @author liuyong
 */
public class SystemConfig {
	public static String USERFILE = null;
	public static String CORPUSFILE = null;
	
	public static String SEPARATOR = " ";
	public static int N_GRAM = 3;
	public static double SRADIUS = 1;
	public static int THRESHOLD = 0;
	public static double WRADIUS = 1;

	public static Trie TRIE = null;
	public static CTrie CTRIE = null;
	public static PTrie PTRIE = null;
	public static BKTree<String> BKTREE = null;

	static {
		try {
			InputStream is = null;
			Properties p = new Properties();
			is = SystemConfig.class.getClassLoader()
					.getResourceAsStream("config/system.config");
			p.load(is);

			USERFILE = p.getProperty("userfile").trim();
			CORPUSFILE = p.getProperty("corpusfile").trim();
			
			SEPARATOR = p.getProperty("separator");
			N_GRAM = Integer.parseInt(p.getProperty("n_gram").trim());
			SRADIUS = Double.parseDouble(p.getProperty("sentence_radius").trim());
			
			WRADIUS = Double.parseDouble(p.getProperty("word_radius").trim());
			THRESHOLD = Integer.parseInt(p.getProperty("threshold").trim());
			
			TRIE = new Trie();
			TRIE.initTrie(USERFILE);

			CTRIE = new CTrie();
			CTRIE.initTrie(CORPUSFILE);

			MetricSpace<String> ms = new LevensteinDistance();
		    List<String> elems = initTree(USERFILE);
		    BKTREE = BKTree.mkBKTree(ms, elems);
		    
		    PTRIE = new PTrie();
		    PTRIE.initTrie(USERFILE);

			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
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
}
