package com.bgi.bktree.trie;

import java.util.concurrent.ConcurrentHashMap;
import com.bgi.util.Node;

/***
 * 基本节点，包含关键词的热度信息
 * @author liuyong
 *
 */
public class BNode extends Node {
    public String name = null;        // 关键词
    public double hot = 0.0f;         // 热度信息
    
    public BNode() {
    	this.end = false;
    	this.values = new ConcurrentHashMap<Character, Node>();
    	this.name = null;
        this.hot = 0.0f;
    }
    
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("end : " + end + "\t");
    	sb.append("name : " + name + "\t");
    	sb.append("map size : " + values.size() + "\t");
    	return sb.toString();
    }
}

