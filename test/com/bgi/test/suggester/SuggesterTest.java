package com.bgi.test.suggester;

import java.util.List;

import com.bgi.suggester.Suggester;
import com.bgi.trie.Trie;

public class SuggesterTest {

	
	public static void main(String[] args) {
		String fileName = "suggest.txt";
		Trie trie = new Trie();
		trie.initTrie(fileName);
		
		Suggester sg = new Suggester(trie, fileName);
		String prefix = "abe";
		List<String> ret = sg.autoCompletion(prefix);
		System.out.println("ret: " + ret);

	}

}
