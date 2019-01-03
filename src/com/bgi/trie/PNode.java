package com.bgi.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PNode {
    public String prefix = null;                              // 关键词
	public List<String> hots = null;                          // 热度列表
	public boolean end = false;                               // 是否结束
	public ConcurrentMap<Character, PNode> values = null;     // 子节点
	
	public PNode() {
		this.prefix = null;
		this.hots = new ArrayList<String>();
		this.end = false;
		this.values = new ConcurrentHashMap<Character, PNode>();
	}
	
	
    public ConcurrentMap<Character, PNode> getChildren() {
    	return this.values;
    }

	
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("prefix : " + prefix + "\t");
    	sb.append("map size : " + values.size() + "\t");
    	sb.append("hot size : " + hots + "\n");
    	return sb.toString();
    }
}
