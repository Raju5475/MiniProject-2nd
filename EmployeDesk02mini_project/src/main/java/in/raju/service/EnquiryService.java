package in.raju.service;

import java.util.List;

import in.raju.binding.DashBoardBinding;
import in.raju.binding.EnquiryForm;
import in.raju.binding.EnquirySearchCriteria;
import in.raju.entity.StudentEntity;

public interface EnquiryService{

	public DashBoardBinding getDashBoarddata(Integer userId);
	
	public List<String> getCoursename();
	
	public List<String> getEnqStatus();
	
	public boolean save_Capture(EnquiryForm enqform);
	
	public List<StudentEntity> getEnquries();
	
	public List<StudentEntity> filter_Enquries(EnquirySearchCriteria criteria,Integer UserId);
	
	public StudentEntity getEnq(Integer id);

	
	public String updateEnq(Integer enqid,EnquiryForm formObj);
	
	
}
