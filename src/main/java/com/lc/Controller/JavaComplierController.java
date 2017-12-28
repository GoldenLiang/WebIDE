package com.lc.Controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lc.service.JavaCompileService;

@CrossOrigin
@Controller
public class JavaComplierController {

	@Resource
	private JavaCompileService javaCompileService;
	
	@ResponseBody
	@RequestMapping(value = "compile")
	public String compile(String javaSource) {
		String executeResult = null;
		try {
			if(StringUtils.isEmpty(javaSource)) {
				return "代码不能为空";
			}
			Class clazz = javaCompileService.compile(javaSource);
			executeResult = javaCompileService.showResult(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return "编译出错了! 错误信息：" + e.getMessage();
		}
		return executeResult;
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
}
