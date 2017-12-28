package com.lc.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {

	Logger logger = LoggerFactory.getLogger(test.class);
	
	@Test
	public void testLog() {
		logger.info("测试");
	}
}
