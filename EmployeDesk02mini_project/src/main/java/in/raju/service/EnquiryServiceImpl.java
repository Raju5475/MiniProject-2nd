package in.raju.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.raju.binding.DashBoardBinding;
import in.raju.binding.EnquiryForm;
import in.raju.binding.EnquirySearchCriteria;
import in.raju.entity.CourseEntity;
import in.raju.entity.EnquirystatusEntity;
import in.raju.entity.StudentEntity;
import in.raju.entity.UserEntity;
import in.raju.repo.CourseRepo;
import in.raju.repo.StudentRepo;
import in.raju.repo.UserRepo;
import in.raju.repo.enquriryStatusRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	
	//TODO inject the user Repo;
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private enquriryStatusRepo enqRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public DashBoardBinding getDashBoarddata(Integer userId) {
		
		// TODO check weather the id which is available in the session is 
		//	available in entity or not By find By id Method
		DashBoardBinding response=new DashBoardBinding();
		
		Optional<UserEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserEntity entity = findById.get();
			List<StudentEntity> enquiries = entity.getEnquiries();
			
			Integer total_Count = enquiries.size();
			Integer enrolled=enquiries.stream()
					  .filter(e -> e.getStatus().equals("Enrolled"))
					  .collect(Collectors.toList()).size();
			
			Integer lost=enquiries.stream()
					  .filter(e -> e.getStatus().equals("Lost"))
					  .collect(Collectors.toList()).size();
			
			response.setTotalENquiries(total_Count);
			response.setEnrolled(enrolled);
			response.setLostenq(lost);
		}
		return response;
		
		
	}
	
	@Override
	public List<String> getCoursename() {
		// TODO get the total course names from the DB
		List<CourseEntity> findAll = courseRepo.findAll();
		List<String> names=new ArrayList<>();

		for(CourseEntity entity : findAll) {
			names.add(entity.getCourse());
		}
		return names;
	}
	@Override
	public List<String> getEnqStatus() {
		
// in the above condition it will give the total data So we need to fetch the just course names
		List<EnquirystatusEntity> findAll = enqRepo.findAll();
		List<String> names=new ArrayList<>();
		for(EnquirystatusEntity enqEntity : findAll) {
			String name = enqEntity.getStatusName();
			names.add(name);
		}
		
		
				return names;
	}
	@Override
	public boolean save_Capture(EnquiryForm enqform) {
		StudentEntity entity=new StudentEntity();
		BeanUtils.copyProperties(enqform, entity);
		Integer UserId =(Integer) session.getAttribute("UserId");
		UserEntity userEntity = userRepo.findById(UserId).get();
		entity.setUser(userEntity);
		studentRepo.save(entity);
		
	return true;
}
	
	
	@Override
	public List<StudentEntity> getEnquries() {
		Integer UserId =(Integer) session.getAttribute("UserId");
		
		 Optional<UserEntity> findById = userRepo.findById(UserId);
		 
		 if(findById.isPresent()) {
			 UserEntity userEntity = findById.get();		
			 List<StudentEntity> enquiries = userEntity.getEnquiries();
			 
			 return enquiries;
		 }
		
		return null;
	}
	
	
	@Override
	public List<StudentEntity> filter_Enquries(EnquirySearchCriteria criteria,Integer UserId) {
		// TODO based on the userId we need to fetch the details
		Optional<UserEntity> findById = userRepo.findById(UserId);
		 
		 if(findById.isPresent()) {
			 UserEntity userEntity = findById.get();		
			 List<StudentEntity> enquiries = userEntity.getEnquiries();
			 
			 //filter Logic
			 
			 if (null != criteria.getCourse() && !"".equals(criteria.getCourse())) {
					enquiries = enquiries.stream().filter(e -> e.getCourse().equals(criteria.getCourse()))
							.collect(Collectors.toList());
				}
				
				if (null != criteria.getStatus() && !"".equals(criteria.getStatus())) {
					enquiries = enquiries.stream().filter(e -> e.getStatus().equals(criteria.getStatus()))
							.collect(Collectors.toList());
				}

				if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
					enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
							.collect(Collectors.toList());
				}
			 
			 //System.out.println(enquiries);
			 
			 return enquiries;
			 
			 
			 
		 }
		return null;
	}
	
	
	@Override
	public StudentEntity getEnq(Integer enqId) {
		Optional<StudentEntity> id = studentRepo.findById(enqId);
		return id.get();
	}
	
	
	@Override
	public String updateEnq(Integer enqid, EnquiryForm formObj) {
		// TODO Get the enqId based on the student Repo
		Optional<StudentEntity> id = studentRepo.findById(enqid);
		if(id.isPresent()) {
			StudentEntity entity=new StudentEntity();
			entity.setName(formObj.getName());
			entity.setPhno(formObj.getPhno());
			entity.setClassMode(formObj.getClassMode());
			entity.setCourse(formObj.getCourse());
			entity.setStatus(formObj.getStatus());
			
		    //Save the new updated enquiries
			studentRepo.save(entity);
			return "Updated";
		}
		
		return "Failed to Update";
	}
}
