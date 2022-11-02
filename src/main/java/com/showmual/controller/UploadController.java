package com.showmual.controller;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.showmual.entity.imageUpload.FileEntity;
import com.showmual.service.FilesService;
import com.showmual.service.ImageClothService;
import com.showmual.service.UserService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class UploadController {
	
	@Autowired
	FilesService filesService;
	@Autowired
	UserService userService;
	@Autowired
	ImageClothService imageClothService;
	
	// 사진 등록 페이지
	@RequestMapping("/user/upload")
	public String Insert() {
		
		return "upload";
	}
	
	// 이미지 저장 + 경로 DB에 넣기
	@RequestMapping(value = "/user/fileUpload", method = RequestMethod.POST)
	public String fileinsert(HttpServletRequest request, @RequestPart MultipartFile files, Principal principal) throws Exception{
		FileEntity file = new FileEntity();
		
		// 로그인한 사용자의 아이디를 가져온다.
		String userId = principal.getName();
		String id = userService.findIdByUserId(userId);
		
		if(!files.isEmpty()) { // 업로드할 파일이 존재할 경우에만
			String sourceFileName = files.getOriginalFilename(); 
			String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
			File destinationFile; 
			String destinationFileName;
			String fileUrl = "E:/Project/images/" + id + "/"; // 아이디 번호로 폴더 생성
			
			do { 
				destinationFileName = 
						RandomStringUtils.randomAlphanumeric(16) + "_" + id + "." + sourceFileNameExtension; 
				destinationFile = new File(fileUrl + destinationFileName); 
			} while (destinationFile.exists());
			
			destinationFile.getParentFile().mkdirs(); 
			files.transferTo(destinationFile);
	
			file.setFilename(destinationFileName);
			file.setFileOriName(sourceFileName);
			file.setFileurl(fileUrl);
			filesService.save(file);
		}
		
		return "redirect:/user/upload";
	}
	
	
	// 분석하기 - HashMap 사용 (JSON 리턴받기)
//	@RequestMapping(value = "/analysis", method = RequestMethod.POST)
//	@ResponseBody
//	public HashMap<String, Object> analysisImage(HttpServletRequest request, @RequestPart MultipartFile files, Principal principal) throws Exception {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		// 로그인한 사용자의 아이디를 가져온다.
//		String email = principal.getName();
//		String id = userService.findIdByEmail(email);
//		
//		if(!files.isEmpty()) { // 업로드할 파일이 존재할 경우에만
//			File destinationFile; 
//			String destinationFileName;
//			// 경로
//			String fileUrl = "E:/Project/images/" + id + "/"; // 아이디 번호로 폴더 생성
//			
//			do {
//				// 이미지 이름
//				destinationFileName = 
//						RandomStringUtils.randomAlphanumeric(16) + "_" + id + ".png"; 
//				destinationFile = new File(fileUrl + destinationFileName); 
//			} while (destinationFile.exists());
//			
//			// destinationFile 경로로 폴더 생성
//			destinationFile.getParentFile().mkdirs(); 
//			files.transferTo(destinationFile);
//			
//			String imgUrl = fileUrl + destinationFileName;
//			
//			// Django로 데이터 보내기
//			ImageClothRepository response = imageClothService.getFirstTodoTest(imgUrl);
//	        System.out.println("=================================");
//	        System.out.println(response.getImage());
//	        System.out.println(response.getQty());
//	        
//	        map.put("Image", response.getImage());
//	        map.put("qty", response.getQty());
//			
//		}
//		return map; //스프링이 자동으로 JSON타입으로 반환해서 전달한다.
//	
//	}
	
	// 분석하기 - HashMap 사용 (JSON 리턴받기)
	@RequestMapping(value = "/analysis", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> analysisImage(HttpServletRequest request, @RequestPart MultipartFile files, Principal principal) throws Exception {
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    
	    FileEntity file = new FileEntity();
	    
	    // 로그인한 사용자의 아이디를 가져온다.
	    String userId = principal.getName();
	    String id = userService.findIdByUserId(userId);
	    
	    if(!files.isEmpty()) { // 업로드할 파일이 존재할 경우에만
	        String sourceFileName = files.getOriginalFilename(); 
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
	        File destinationFile; 
	        String destinationFileName;
	        // 경로
	        String fileUrl = "E:/Project/images/" + id + "/"; // 아이디 번호로 폴더 생성
	        
	        do {
	            // 이미지 이름
	            destinationFileName = 
	                    RandomStringUtils.randomAlphanumeric(16) + "_" + id + "." + sourceFileNameExtension; 
	            destinationFile = new File(fileUrl + destinationFileName); 
	        } while (destinationFile.exists());
	        
	        // destinationFile 경로로 폴더 생성
	        destinationFile.getParentFile().mkdirs(); 
	        files.transferTo(destinationFile);
	        
	        // DB에 넣기
            file.setFilename(destinationFileName);
            file.setFileOriName(sourceFileName);
            file.setFileurl(fileUrl);
            filesService.save(file);
	        
	        String imgUrl = fileUrl + destinationFileName;
	        
	        map.put("image", imgUrl);
	        map.put("qty", 3);
	        
	    }
	    return map; //스프링이 자동으로 JSON타입으로 반환해서 전달한다.
	    
	}
	
	
	// 분석하기 - HashMap 사용 (JSON 리턴받기)
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> analysisTest() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
        map.put("image", "E:/Project/images/1/gaFI7We4wBLawcpl_1.jpg");
        map.put("qty", 3);
		
		return map;
	
	}
	
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	@ResponseBody
//	public ImageClothRepository analysisTest() {
//	    ImageClothRepository imageClothRepository = new ImageClothRepository();
//	    
//	    imageClothRepository.setImage("E:/Project/images/1/gaFI7We4wBLawcpl_1.jpg");
//	    imageClothRepository.setQty(3);
//	    
//	    return imageClothRepository;
//	    
//	}
	
	// 테스트 페이지
    @RequestMapping("/user/imageTest")
    public String imageTest() {
        
        return "imageTest";
    }
	
	
//	// 이미지 경로 보내기
//	@RequestMapping(value = "/user/fileUpload", method = RequestMethod.POST)
//	public String imageUrl(HttpServletRequest request, @RequestPart MultipartFile files, Principal principal, Model model) throws Exception {
//		
//		Files file = new Files();
//		
//		// 로그인한 사용자의 아이디를 가져온다.
//		String email = principal.getName();
//		String id = userService.findIdByEmail(email);
//		
//		if(!files.isEmpty()) { // 업로드할 파일이 존재할 경우에만
//			String sourceFileName = files.getOriginalFilename(); 
//			String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
//			File destinationFile; 
//			String destinationFileName;
//			// 경로
//			String fileUrl = "E:/Project/images/" + id + "/"; // 아이디 번호로 폴더 생성
//			
//			do {
//				// 이미지 이름
//				destinationFileName = 
//						RandomStringUtils.randomAlphanumeric(16) + "_" + id + "." + sourceFileNameExtension; 
//				destinationFile = new File(fileUrl + destinationFileName); 
//			} while (destinationFile.exists());
//			
//			destinationFile.getParentFile().mkdirs(); 
//			files.transferTo(destinationFile);
//			
//			file.setFilename(destinationFileName);
//			file.setFileOriName(sourceFileName);
//			file.setFileurl(fileUrl);
//			filesService.save(file);
//			
//			String imgUrl = fileUrl + destinationFileName;
//			
//			// Django로 데이터 보내기
//			ImageClothRepository response = imageClothService.getFirstTodoTest(imgUrl);
//	        System.out.println("=================================");
//	        System.out.println(response.getKind());
//	        System.out.println(response.getQty());
//	        
//	        model.addAttribute("kind", response.getKind());
//	        model.addAttribute("qty", response.getQty());
//			
//		}
//		
//		return "redirect:/user/upload";
//	}
}
