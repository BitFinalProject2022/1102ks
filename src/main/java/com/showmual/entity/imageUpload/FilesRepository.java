package com.showmual.entity.imageUpload;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FilesRepository extends JpaRepository<FileEntity, Integer> {
	
	FileEntity findByFno(int fno);
}
