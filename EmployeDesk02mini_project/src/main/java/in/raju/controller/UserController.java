package in.raju.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.raju.binding.LoginBinding;
import in.raju.binding.SingnInBindind;
import in.raju.binding.UnlockBinding;
import in.raju.service.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl service;
	
	@Autowired
	private HttpSession session;

	// to capture the sign up form data
	@PostMapping("/signup")
	public String handle_singn( SingnInBindind signin, Model model) {
		boolean status = service.signup(signin);

		if (status) {
			model.addAttribute("succMSg", "Temporary password sent to mail please check");
		} else {
			model.addAttribute("errMsg", "This mail id already exists");
		}
		init(model);
		return "signup";
	}

	private void init(Model model) {
		model.addAttribute("user", new SingnInBindind());
	}

	@GetMapping("/signup")
	public String sinuppage(Model model) {
		init(model);
		return "signup";
	}
@GetMapping("/login")
	public String loginPage(Model model) {
		initlogin(model);
		return "login";
	}

private void initlogin(Model model) {
	model.addAttribute("login", new LoginBinding());
}
	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginBinding login,Model model) {
		String status = service.login(login);
		if (status.contains("success")) {
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg",status);
		initlogin(model);
		return "login";
	}


	@GetMapping("/unlock")
	public String unlockpage( @RequestParam String email,Model model) {
		UnlockBinding unlockform= new UnlockBinding();
		unlockform.setEmail(email);
		model.addAttribute("unlock", unlockform);
		
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockAcc(UnlockBinding unlockform,Model model) {
		
		if(unlockform.getNewPW().equals(unlockform.getCnfrmPw())) {
			boolean status = service.unlock(unlockform);
			
			if(status) {
				
				model.addAttribute("success", "Your Account is unlocked Sucessfully");
			} else {
			model.addAttribute("error", "Temporary Password Doesnt Match Sucessfully");
		}
			
		}else {
			model.addAttribute("errors", "ohhhh..!Both feilds Doesnt Match...!");
			
		}
		
		model.addAttribute("unlock", unlockform);
		
		return "unlock";
	}
	
	@GetMapping("/forgot")
	public String forgotpwpage() {
		
		
		return "forgotpw";
	}
	
	@PostMapping("/forgot")
	public String Handelforgotpage(@RequestParam("email")  String email,Model model) {
		
	boolean status = service.forgotPw(email);
		if(status) {
			model.addAttribute("succMsg","Password Sent is mail please check");
		}else {
		model.addAttribute("error","The mail id doesn't exist's");
		}
		return "forgotpw";
	}
	
	

}
