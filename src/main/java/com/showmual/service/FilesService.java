package com.showmual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showmual.entity.imageUpload.FileEntity;
import com.showmual.entity.imageUpload.FilesRepository;


@Service
public class FilesService {
	
	@Autowired
	FilesRepository filesRepository;
	
	public void save(FileEntity files) {
		FileEntity f = new FileEntity();
		f.setFilename(files.getFilename());
		f.setFileOriName(files.getFileOriName());
		f.setFileurl(files.getFileurl());
		
		filesRepository.save(f);
	}
}
