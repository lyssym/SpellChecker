package com.bgi.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bgi.io.IOUtil;

public class Trie {
	protected Node root = null;
	
	public Trie() {
		root = new Node();
	}


	/**
	 * 插入节点信息
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
				addKey(arr[0], Double.parseDouble(arr[1]));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    /***
     * 插入节点
     * @param word
     */
    public void addKey(String word, double hot) {
        Node node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            Node subNode = node.values.get(c);
            if (subNode == null) {
                subNode = new Node();
                node.values.put(c, subNode);
            }
            node = subNode;
        }
         
        node.end = true;
        node.name = word;
        node.hot = hot;
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
        
        Node node = root;
        int size = word.length();
        Node subNode = null;
        for (int i = 0; i < size; i++) {
            char c = word.charAt(i);
            subNode = node.values.get(c);
            if (subNode == null) { // 节点不存在
                return -1;
            }
            node = subNode;
        }
        
        if (node.end) { // 节点匹配命中
        	return node.hot;
        } else { // 节点不存在
        	return -1;
        }
    }
    

    /**
     * 删除节点
     * @param word
     */
    public void deleteKey(String word) {
        deleteKey(root, word, 0);
    }

    
    /**
     * 删除词汇信息
     * @param word
     * @return 
     */
    public boolean deleteKey(Node current, String word, int index) {
        if (index == word.length()) {
            if (!current.end) { // 节点未结束
                return false;
            }
            
            current.end = false;
            return current.getChildren().isEmpty();
        }
        
        char ch = word.charAt(index);
        Node node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        
        // 递归调用
        boolean shouldDeleteCurrentNode = deleteKey(node, word, index+1) && !node.end;
        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(ch);
            return current.getChildren().isEmpty();
        }
        
        return false;
    }
    
    
    /***
     * 更新节点
     * @param word
     */
    public void updateKey(String word, double hot) {
        addKey(word, hot);
    }
    
    
    /**
     * 构建键树时
     * @param word
     * @return
     */
	public List<Element> searchKeys(String word) {
		if (word == null || "".equals(word)) {
			return null;
		}

		List<Element> ret = new ArrayList<Element>();
		Node node = root;
		Node subNode = null;
		int size = word.length();
		for (int i = 0; i < size; i++) {
			char c = word.charAt(i);
			subNode = node.values.get(c);
			if (subNode == null) {
				return null;
			}
			node = subNode;
		}
		
		if (subNode == null) {
			return null;
		}
		
		searchKeys(subNode, ret);
		return ret;
	}
	
	
    /**
     * 检索前缀, 并返回该前缀下的所有关键词及热度信息
     * @param word
     * @return 
     */
    private void searchKeys(Node node, List<Element> result) {
    	ConcurrentHashMap<Character, Node> values = node.values;
        for (Map.Entry<Character, Node> entry: values.entrySet()) {
        	Node subNode = entry.getValue();
        	if (subNode == null) {
        		continue;
        	}

        	if (subNode.end) { // 若是叶子节点
        		result.add(new Element(subNode.name, subNode.hot));
        	}
        	
        	searchKeys(subNode, result);
        }
    }
}

