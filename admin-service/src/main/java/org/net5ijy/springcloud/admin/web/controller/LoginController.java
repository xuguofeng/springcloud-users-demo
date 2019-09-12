package org.net5ijy.springcloud.admin.web.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * org.net5ijy.springcloud.admin.web.controller.LoginController 类
 *
 * @author xuguofeng
 * @date 2019/5/29 11:18
 */
@RestController
public class LoginController {

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@GetMapping("/login-error")
	public ModelAndView loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
		return new ModelAndView("login", "userModel", model);
	}

	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		response.sendRedirect("/login");
	}

	@GetMapping(value = "/Access_Denied")
	public ModelAndView accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return new ModelAndView("accessDenied", "userModel", model);
	}

	@GetMapping(value = "/error50x")
	public ModelAndView error50x(ModelMap model) {
		return new ModelAndView("error50x", "userModel", model);
	}

	private String getPrincipal() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		} else {
			return principal.toString();
		}
	}
}
