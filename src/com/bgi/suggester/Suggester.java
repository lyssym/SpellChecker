package com.bgi.suggester;

import java.util.List;

import com.bgi.bktree.trie.Trie;
import com.bgi.suggester.trie.PTrie;
import com.bgi.util.SystemConfig;

public class Suggester {
	private PTrie trie = SystemConfig.PTRIE;
	
	
	public Suggester() {
	}
	
	
	public Suggester(Trie trie) {
		this.trie = new PTrie(trie);
	}
	
	
	public List<String> autoCompletion(String prefix) {
		return trie.queryKey(prefix);
	}

}
