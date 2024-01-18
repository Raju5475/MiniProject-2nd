package in.raju.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.raju.entity.CourseEntity;

public interface CourseRepo  extends JpaRepository<CourseEntity, Integer>{

}
