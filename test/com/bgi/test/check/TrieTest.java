package com.bgi.test.check;

import com.bgi.bktree.trie.Trie;


public class TrieTest {

	public static void main(String[] args) {
		Trie trie = new Trie();
		String fileName = "data.txt";
		String word = "hello";
		
		trie.initTrie(fileName);
		
		double ret = trie.searchKey(word);
		System.out.println(ret);
		
		trie.deleteKey(word);
		
		ret = trie.searchKey(word);
		System.out.println(ret);
		
		word = "hell";
		ret = trie.searchKey(word);
		System.out.println(ret);

	}

}
