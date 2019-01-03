package com.bgi.suggester;

import java.util.List;

import com.bgi.trie.PTrie;
import com.bgi.trie.Trie;

public class Suggester {
	private PTrie trie = null;
	
	public Suggester(Trie trie, String fileName) {
		this.trie = new PTrie(trie);
		this.trie.initTrie(fileName);
	}
	
	
	public List<String> autoCompletion(String prefix) {
		return this.trie.queryKey(prefix);
	}

}
