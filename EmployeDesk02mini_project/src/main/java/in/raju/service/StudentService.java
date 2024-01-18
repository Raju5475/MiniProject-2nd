package in.raju.service;

import java.util.*;
import in.raju.binding.DashBoardBinding;
import in.raju.binding.EnquiryForm;
import in.raju.binding.EnquirySearchCriteria;

public interface StudentService {
	
	public List<String> getcourses();
	
	public List<String> getstatus();

	public DashBoardBinding getDashboard(Integer userId);
	
	public String upsertEnquiry(EnquiryForm eqform);
	
	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria search);
	
	public EnquiryForm getenquiry(Integer enqId);
	
	
}
