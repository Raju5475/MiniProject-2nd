package in.raju.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.raju.binding.DashBoardBinding;
import in.raju.binding.EnquiryForm;
import in.raju.binding.EnquirySearchCriteria;
import in.raju.entity.StudentEntity;
import in.raju.repo.StudentRepo;
import in.raju.repo.UserRepo;
import in.raju.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService service;

	@Autowired
	private StudentRepo studentRepo;
	
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();

		return "index";
	}

	@GetMapping("/dashboard")
	public String dashBoardpage(Model model) {
		Integer Id = (Integer) session.getAttribute("UserId");
		DashBoardBinding dashBoard = service.getDashBoarddata(Id);
		model.addAttribute("dashboard", dashBoard);
		return "dashboard";
	}

	@GetMapping("/add-enquiry")
	public String addEnquriesPage(Model model) {

		// get courses names form db
		List<String> coursename = service.getCoursename();

		// get the enq status from db
		List<String> enqStatus = service.getEnqStatus();

		// Create form Binding Object
		EnquiryForm enqform = new EnquiryForm();

		// add it to the Model Object

		initForm(model, coursename, enqStatus, enqform);

		return "addenquires";

	}

	private void initForm(Model model, List<String> coursename, List<String> enqStatus, EnquiryForm enqform) {
		model.addAttribute("courses", coursename);
		model.addAttribute("status", enqStatus);
		addEnqUi(model, enqform);
	}

	private void addEnqUi(Model model, EnquiryForm enqform) {
		model.addAttribute("enqForm", enqform);
	}

	@PostMapping("/addEnq")
	public String CaptureData(Model model, EnquiryForm enqForm) {
		// Save the Data in the Data Base
		boolean status = service.save_Capture(enqForm);
		if (status) {
			model.addAttribute("succMsg", "Enquiry added Sucessfully...!");
		} else {
			model.addAttribute("error", "Couldn't add enquiry problem Occured..!");
		}
		addEnqUi(model, enqForm);
		return "addenquires";
	}

	@GetMapping("/view")
	public String viewEnquriesPage(Model model) {

		List<StudentEntity> enquries = service.getEnquries();

		// get courses names form db
		List<String> coursename = service.getCoursename();

		// get the enq status from db
		List<String> enqstatus = service.getEnqStatus();
		
		//extractedview(model, coursename, enqstatus);
		model.addAttribute("enqstatus", enqstatus);
		model.addAttribute("coursename", coursename);
		model.addAttribute("enquries", enquries);
		return "view_enquiry";

	}

	private void extractedview(Model model, List<String> coursename, List<String> enqstatus) {
		model.addAttribute("enqstatus", enqstatus);
		model.addAttribute("coursename", coursename);
	}
	
	
	@GetMapping("/filter-enquiries")
	public String getFilterEnq(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourse(cname);
		criteria.setClassMode(mode);
		criteria.setStatus(status);
		System.out.println(criteria);

		Integer userId = (Integer) session.getAttribute("UserId");
		List<StudentEntity> filter_Enquries = service.filter_Enquries(criteria, userId);
		model.addAttribute("enquries", filter_Enquries);
		
	return "NewEnquirys";
	}
	
	@GetMapping("/enqu")
	public String enquires(Model model) {
		
		
		EnquiryForm formObj=new EnquiryForm();
		
		if(session.getAttribute("enq")!=null) {
			StudentEntity enq = (StudentEntity)session.getAttribute("enq");
			   BeanUtils.copyProperties(enq, formObj);
			   session.removeAttribute("enq");
		}
		model.addAttribute("enqForm", formObj);
		enqinit(model, formObj);
		
		return "addenquires";
	}

	private void enqinit(Model model, EnquiryForm formObj) {
		model.addAttribute("enqForm", formObj);
		model.addAttribute("courses", service.getCoursename());
		model.addAttribute("status", service.getEnqStatus());
	}
	
	
	
	@GetMapping("/edit/{id}")
	public String editFunction(@PathVariable("id") Integer id) {
		
		StudentEntity enq = service.getEnq(id);
		
		session.setAttribute("enq", enq);
		session.setAttribute("enqid", id);
		return "redirect:/enqu";
		
	}
	@GetMapping("/delete/{enqId}")
	public String deleteData(@PathVariable("enqId")Integer enqId) {
		studentRepo.deleteById(enqId);
		return "redirect:/view";
	}
	

}
