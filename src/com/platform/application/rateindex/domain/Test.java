package com.platform.application.rateindex.domain;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test {
	
	public static void main(String[] args){
		
		
		ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
		
		try {  
            System.out.println(jse.eval("0==18.0"));  
        } catch (Exception t) {  
        }  
		
		
//		double a=2;
//		double b=8;
//		
//		for(int i=1; i<50; i++){
//			System.out.println(i+"========="+a/(i));
//		}
//		
		
		
		
		
		
		
		
		//ScriptEngineManager manager = new ScriptEngineManager();
        //ScriptEngine engine = manager.getEngineByName("js");
	}

}
