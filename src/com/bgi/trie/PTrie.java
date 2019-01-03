package com.bgi.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bgi.io.IOUtil;

public class PTrie {
	private PNode root = null;
	private Trie trie = null;
	
	public PTrie(Trie trie) {
		root = new PNode();
		this.trie = trie;
	}

	/**
	 * 根据任务插入节点信息
	 * @param head
	 * @param synonym
	 * @param category
	 */
	public void initTrie(String fileName) {
		try {
			BufferedReader br = IOUtil.loadResource(fileName, 2);
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] arr = s.split("\t");
				String key = arr[0].trim();
				if (!searchKey(key)) {
					addKey(key);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void sort(List<Element> result) {
		Collections.sort(result, new Comparator<Element>() {
            @Override
            public int compare(Element src, Element dst) {
                if (src.hot >= dst.hot) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
	}
	
	
	private List<String> extract(List<Element> result) {
		if (result == null ) return null;
		int size = result.size();
		size = size > 10 ? 10 : size;
		List<String> ret = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			Element e = result.get(i);
			ret.add(e.name);
		}
		return ret;
	}

	
    /***
     * 插入新节点
     * @param word
     */
    public void addKey(String word) {
        PNode node = root;
        int len = word.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            sb.append(c);
            String key = sb.toString();
            List<Element> ret = trie.searchKeys(key);
 
            sort(ret);
            
            PNode subNode = node.values.get(c);
            if (subNode == null) {
                subNode = new PNode();
                node.values.put(c, subNode);
            }
            
            node = subNode;
            node.prefix = key;
            node.end = true;
            node.hots = extract(ret);
        }
    }
    
    
    /***
     * 检索节点
     * @param word
     */
    public boolean searchKey(String word) {
        PNode node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            PNode subNode = node.values.get(c);
            
            if (subNode == null) {
                return false;
            }
            node = subNode;
        }
        
        return node.end;
    }
    
    
    /***
     * 更新节点信息
     * @param word
     */
    public void updateKey(String word, List<String> result) {
        PNode node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            PNode subNode = node.values.get(c);
            if (subNode == null) {
                subNode = new PNode();
                node.values.put(c, subNode);
            }
            
            node = subNode;
        }
        
        node.prefix = word;
        node.end = true;
        node.hots = result;
    }

    
    /**
     * 实时自动补全
     * @param word
     * @return
     */
    public List<String> queryKey(String word) {
        if (word == null || "".equals(word)) {
            return null;
        }
        
        PNode node = root;
        PNode subNode = null;
        
        int size = word.length();
        for (int i = 0; i < size; i++) {
            char c = word.charAt(i);
            subNode = node.values.get(c);
            if (subNode == null) { // 节点不存在
                return null;
            }

            node = subNode;
        }
        
        return node.hots;
    }
    
    
    /**
     * 删除节点
     * @param word
     */
    public void deleteKey(String word) {
        deleteKey(root, word, 0);
    }

    
    /**
     * 删除词汇节点
     * @param word
     * @return 
     */
    public boolean deleteKey(PNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.end) { // 节点未结束
                return false;
            }
            
            current.end = false;
            return current.getChildren().isEmpty();
        }
        
        char c = word.charAt(index);
        PNode node = current.getChildren().get(c);
        if (node == null) {
            return false;
        }

        
        // 递归调用
        boolean shouldDeleteCurrentNode = deleteKey(node, word, index+1) && !node.end;
        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(c);
            return current.getChildren().isEmpty();
        }
        
        return false;
    }
    
}
