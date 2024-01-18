package in.raju.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.raju.entity.StudentEntity;

public interface StudentRepo extends JpaRepository<StudentEntity, Integer>{

}
