package com.showmual.entity.imageUpload;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class FileEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_Increment인 컬럼
	int fno;
	
	String filename;
	String fileOriName;
	String fileurl;
	
}
