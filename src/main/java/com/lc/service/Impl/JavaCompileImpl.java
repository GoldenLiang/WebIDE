package com.lc.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lc.Exception.CompileException;
import com.lc.Util.ClassClassLoader;
import com.lc.Util.Constans;
import com.lc.service.JavaCompileService;

import  static com.lc.Util.Constans.className;;

@Service
public class JavaCompileImpl implements JavaCompileService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Class compile(String javaSource) throws Exception {
		StringObject so = new StringObject(className, javaSource);
		JavaFileObject files = so;
		//获取编译器
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();	
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		
		//文件对象迭代器
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(files);
		//执行编译任务
		Boolean result = compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
		if(result) {
			logger.info("编译成功!");
		} else {
			logger.info("编译失败!!!");
			throw new CompileException("Compile Error");
		}
		return loadClass(className);
	}

	/**
	 * 加载 Class
	 * @param className
	 * @return
	 * @throws Exception 
	 */
	public Class loadClass(String className) throws Exception {
		//自定义 classLoader
		ClassClassLoader classLoader = new ClassClassLoader(getClass().getClassLoader());
		Class<?>  clazz = classLoader.loadClass(className);
		return clazz;
	}
	
	/**
	 * 显示结果
	 */
	@Override
	public String showResult(Class clazz) throws Exception {
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
		PrintStream cacheStream = new PrintStream(baoStream);
		PrintStream oldStream = System.out;
		//输出结果保存在 baoStream 里
		System.setOut(cacheStream);
		
		//执行 Main 方法
		Method method = clazz.getMethod(Constans.executeMainMethodName, String[].class);
		method.invoke(null, (Object)new String[] {});
		
		//输出结果打印到控制台
		System.setOut(oldStream);
		return baoStream.toString();
	}

	private class StringObject extends SimpleJavaFileObject {

		private String contents = null;
		
		protected StringObject(String className, String contents) {
			super(URI.create("String:///" + className + Kind.SOURCE.extension), Kind.SOURCE);
			this.contents = contents;
		}
		
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return contents;
		}
	
	}
	
	public static void main(String[] args) {
		String code = "public class Main {\n" + "\tpublic static void main(String[] args){\n"
				+ "\t\tSystem.out.println(\"hello\t\tworld\");\n" + "\t}\n" + "}";
		System.out.println(code);
		JavaCompileImpl javaComplieService = new JavaCompileImpl();
		try {
			Class clazz = javaComplieService.compile(code);
			String result = javaComplieService.showResult(clazz);
			System.out.println("--------->" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}

