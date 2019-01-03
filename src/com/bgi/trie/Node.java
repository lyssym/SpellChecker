package com.bgi.trie;

import java.util.concurrent.ConcurrentHashMap;

public class Node {
    public String name = null;                               // 关键词
    public boolean end = false;                              // 是否结束
    public double hot = 0.0f;                                // 热度
    public ConcurrentHashMap<Character, Node> values = null; // 子节点
    
    public Node() {
    	this.end = false;
    	this.values = new ConcurrentHashMap<Character, Node>();
    	this.name = null;
        this.hot = 0.0f;
    }
    
    
    public ConcurrentHashMap<Character, Node> getChildren() {
    	return this.values;
    }

    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("end : " + end + "\t");
    	sb.append("name : " + name + "\t");
    	sb.append("map size : " + values.size() + "\t");
    	return sb.toString();
    }
}

