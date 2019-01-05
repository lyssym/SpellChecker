package com.bgi.util;

import java.util.concurrent.ConcurrentHashMap;

public class Node {
    public boolean end = false;                              // 是否结束
    public ConcurrentHashMap<Character, Node> values = null; // 子节点
    
    public Node() {
    	this.end = false;
    	this.values = new ConcurrentHashMap<Character, Node>();
    }
    
    
    public ConcurrentHashMap<Character, Node> getChildren() {
    	return this.values;
    }

    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("end : " + end + "\t");
    	sb.append("map size : " + values.size() + "\t");
    	return sb.toString();
    }
}
