package com.bgi.bktree;

public class Element<T> {
	public T data = null;
	public double hot = 0;
	
	public Element(T data, double hot) {
		this.data = data;
		this.hot = hot;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: " + data + "\t");
		sb.append("hot: " + hot + "\n");
		return sb.toString();
	}

}
