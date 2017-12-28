package com.lc.Util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ClassClassLoader extends ClassLoader {

	private String path = Constans.classPath;
	
	public ClassClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		//包转为目录
		String classPath = name.replace(".", "\\") + ".class";	
		String classFile = path + classPath; 
		Class clazz = null;
		
		try {
			byte[] data = getFileBytes(classFile);
			clazz = defineClass(name, data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	private byte[] getFileBytes(String classFile) throws Exception {
		//NIO读取
		FileInputStream fis = new FileInputStream(classFile);
		FileChannel fileC = fis.getChannel();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		WritableByteChannel outC = Channels.newChannel(baos);
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		while(true) {
			int i = fileC.read(buffer);
			if(i == 0 || i == -1) {
				break;
			}
			buffer.flip();
			outC.write(buffer);
			buffer.clear();
		}
		fis.close();
		return baos.toByteArray();
	}
}
