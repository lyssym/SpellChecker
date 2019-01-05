package com.bgi.suggester.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.bgi.util.Node;

/***
 * 前缀节点，针对前缀提供热度信息
 * @author liuyong
 *
 */

public class PNode extends Node {
	public String prefix = null;            // 前缀信息    
	public List<String> hots = null;        // 热度列表

	public PNode() {
		this.prefix = null;
		this.hots = new ArrayList<String>();
		this.end = false;
		this.values = new ConcurrentHashMap<Character, Node>();
	}


    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("prefix : " + prefix + "\t");
    	sb.append("map size : " + values.size() + "\t");
    	sb.append("hot size : " + hots + "\n");
    	return sb.toString();
    }
}
