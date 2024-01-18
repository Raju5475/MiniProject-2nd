package in.raju.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Table(name="AIT_Students")
@Entity
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;  
	private String name;
	private Long phno;
	private String classMode;
	private String course;
	private String status;
	
	@CreationTimestamp
	private LocalDate creadtedDate;
	
	@UpdateTimestamp
	private LocalDate startedDate;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private UserEntity user;
	
	
	
	
}
