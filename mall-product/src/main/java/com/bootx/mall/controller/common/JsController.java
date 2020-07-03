
package com.bootx.mall.controller.common;

import javax.servlet.http.HttpServletResponse;

import com.bootx.mall.util.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - Js
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("commonJsController")
@RequestMapping("/resources/common/js")
public class JsController {

	@Value("${javascript_content_type}")
	private String javascriptContentType;
	@Value("${security.member_login_url}")
	private String memberLoginUrl;
	@Value("${security.business_login_url}")
	private String businessLoginUrl;
	@Value("${security.admin_login_url}")
	private String adminLoginUrl;

	/**
	 * 基础
	 */
	@GetMapping("/base.js")
	public String base(HttpServletResponse response, ModelMap model) {
		response.setContentType(javascriptContentType);
		model.addAttribute("memberLoginUrl", memberLoginUrl);
		model.addAttribute("businessLoginUrl", businessLoginUrl);
		model.addAttribute("adminLoginUrl", adminLoginUrl);
		model.addAttribute("setting", SystemUtils.getSetting());
		return "common/js/base";
	}

}