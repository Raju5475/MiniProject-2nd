package in.raju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/load")
	public String getIndex() {
		return "index";
	}
	
}
