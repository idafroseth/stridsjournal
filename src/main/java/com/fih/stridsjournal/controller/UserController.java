package com.fih.stridsjournal.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fih.stridsjournal.model.User;
import com.fih.stridsjournal.service.UserService;



@Controller
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService){
		this.userService = userService;
	}
	
	@ModelAttribute("account")
	public String getCurrentUser(Principal principal) {
		if(principal == null){
			return null;
		}
		return principal.getName();
	}

	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String getPromoPage() {
		return "/index";
	}
    
    
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String getLoginPage() {
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/index?logout";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage(){
		return "/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User userToRegister,
			BindingResult bindingResult, @ModelAttribute("g-recaptcha-response") String captcha, HttpServletRequest request) {
	
		 if (userService.userExists(userToRegister.getUsername())) {
				bindingResult.addError(new ObjectError("username", "E-postadressen er allerede registrert."));
				return "/register";
		 }

		 userService.saveUser(userToRegister);
		return "redirect:/login";
	}
	
    @GetMapping("/pp")
    public String getPrivacyPolicy(){
    	return "/PrivacyPolicy";
    }
    @GetMapping("/toc")
    public String getTersomOfCondition(){
    	return "/TermsOfCondition";
    }


	
}
