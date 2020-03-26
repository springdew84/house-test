package com.cassey.house.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * java内部调用js函数
 * @author chunyang.zhao
 *
 */
public class TestScriptEngineManager {
	
	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager manager= new ScriptEngineManager();
		ScriptEngine engineByName = manager.getEngineByName("JavaScript");
		String js = ""
				+ "var a = 1;"
				+ "var b = 2;"
				+ "var c = a+b;"
				+ "function sum(a,b,c){"
				+ "return a+b+c"
				+ "}"
				+ "sum(a,b,c);";
		//Object eval = engineByName.eval(js);
		//System.err.println(eval);

		String value1 = "deal";
		boolean isArticlePostSupported = false;
		if(("post".equals(value1) || "guide".equals(value1)) && isArticlePostSupported) {
			String t = "sss";
			String s = t;
		} else {
			String t1 = "sss";
			String s1 = t1;
		}

	}	
}