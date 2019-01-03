package com.bgi.trie;

public class Element {
	public String name = null;
	public double hot = 0;
	
	public Element(String name, double hot) {
		this.name = name;
		this.hot = hot;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: " + name + "\t");
		sb.append("hot: " + hot + "\n");
		return sb.toString();
	}

}
