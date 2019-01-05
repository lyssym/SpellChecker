package com.bgi.test.suggester;

import java.util.List;

import com.bgi.suggester.Suggester;

public class SuggesterTest {

	public static void main(String[] args) {
		Suggester sg = new Suggester();
		String prefix = "abe";
		List<String> ret = sg.autoCompletion(prefix);
		System.out.println("ret: " + ret);
	}

}
