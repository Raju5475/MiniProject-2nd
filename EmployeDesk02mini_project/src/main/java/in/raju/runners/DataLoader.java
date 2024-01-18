package in.raju.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.raju.entity.CourseEntity;
import in.raju.entity.EnquirystatusEntity;
import in.raju.repo.CourseRepo;
import in.raju.repo.enquriryStatusRepo;


@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private enquriryStatusRepo enqRepo;
	
	
@Override
public void run(ApplicationArguments args) throws Exception {
	courseRepo.deleteAll();
	
	CourseEntity c1=new CourseEntity();
	c1.setCourse("Java");
	
	CourseEntity c2=new CourseEntity();
	c2.setCourse("Python");
	
	CourseEntity c3=new CourseEntity();
	c3.setCourse("Aws");
	
	CourseEntity c4=new CourseEntity();
	c4.setCourse("Devops");
	
	CourseEntity c5=new CourseEntity();
	c5.setCourse("SpringBoot");
	
	CourseEntity c6=new CourseEntity();
	c6.setCourse("C++");
	
	courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4,c5,c6));
	
	
	enqRepo.deleteAll();
	
	EnquirystatusEntity status1=new EnquirystatusEntity();
	status1.setStatusName("New");
	
	EnquirystatusEntity status2=new EnquirystatusEntity();
	status2.setStatusName("Enrolled");
	
	EnquirystatusEntity status3=new EnquirystatusEntity();
	status3.setStatusName("Lost");
	
	enqRepo.saveAll(Arrays.asList(status1,status2,status3));
	
}
}
