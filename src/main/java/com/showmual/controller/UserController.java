package com.showmual.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.showmual.model.UserDto;
import com.showmual.service.UserService;
import com.showmual.validate.CheckUserIdValidator;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
	
//	@Autowired
//	FilesService filesService;
	
	private final UserService userService;
	private final CheckUserIdValidator checkUserIdValidator;
	
	/* 커스텀 유효성 검증을 위해 추가 */
	@InitBinder
	public void validatorBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(checkUserIdValidator);
	}
	
	// 메인 페이지
	@GetMapping("/")
	public String index() {
		return "index";
	}

	// 회원가입 페이지
//	@GetMapping("/user/signup")
//	public String dispSignup() {
//		return "signup";
//	}

//    // 회원가입 처리
//    @PostMapping("/user/signup")
//    public String execSignup(MemberDto memberDto) {
//        memberService.joinUser(memberDto);
//
//        return "redirect:/user/login";
//    }

	// 회원가입 처리
	@PostMapping("/user/signup")
	public String execSignup(@Valid UserDto userDto, Errors errors, Model model) {
		if (errors.hasErrors()) {

			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}

			return "login";
		}

		userService.joinUser(userDto);
		return "redirect:/user/login";
	}
	
	// 아이디 중복체크
    @RequestMapping(value = "/user/idCheck", method = RequestMethod.POST)
    @ResponseBody
    public Long idCheck(@RequestParam("id") String id) {
        
        Long cnt = userService.countByUserId(id);
        return cnt;
    }
	
    // 닉네임 중복체크
    @RequestMapping(value = "/user/nicknameCheck", method = RequestMethod.POST)
    @ResponseBody
    public Long nicknameCheck(@RequestParam("nickname") String nickname) {
        
        Long cnt = userService.countByNickname(nickname);
        return cnt;
    }
    
    
	// 로그인 페이지
	@GetMapping("/user/login")
	public String login() {
		return "login";
	}

	// 로그인 결과 페이지
//	@GetMapping("/user/login/result")
//	public String dispLoginResult() {
//		return "loginSuccess";
//	}

	// 로그아웃 결과 페이지
//	@GetMapping("/user/logout/result")
//	public String dispLogout() {
//		return "logout";
//	}

	// 접근 거부 페이지
	@GetMapping("/user/denied")
	public String dispDenied() {
		return "denied";
	}

	// 내 정보 페이지
	@GetMapping("/user/myinfo")
	public String dispMyInfo() {
		return "myinfo";
	}

	// 어드민 페이지
//	@GetMapping("/admin")
//	public String dispAdmin() {
//		
//		return "admin";
//	}
	
	// Contact 페이지
//	@RequestMapping("/user/memberinfo")
//	@ResponseBody
//	public String Contact() {
//		
//		return "Contact";
//	}
	
//	@RequestMapping("/user/Contact")
//	@ResponseBody
//	public String memberInfo(Principal principal) {
//		String email = principal.getName();
//		
//		return email;
//	}
	
	// 사용자 정보 보기
//	@RequestMapping("/user/memberid")
//	@ResponseBody
//	public String memberId(Principal principal) {
//		String email = principal.getName();
//		String id = memberService.findIdByEmail(email);
//		
//		return id;
//	}
	
	// 나만의 옷장 페이지
	@GetMapping("/user/closet")
	public String closet() {
		return "closet";
	}
	
}
