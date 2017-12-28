package com.lc.service;

public interface JavaCompileService {
 	
	String showResult(Class clazz) throws Exception;

	Class compile(String javaSource) throws Exception;
	
}
