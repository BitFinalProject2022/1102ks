package com.showmual.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.showmual.entity.user.UserEntity;
import com.showmual.entity.user.UserRepository;
import com.showmual.model.Role;
import com.showmual.model.UserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;

	// 회원가입 기능
    @Transactional
    public Long joinUser(UserDto userDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return userRepository.save(userDto.toEntity()).getId();
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityWrapper = userRepository.findByUserId(userId);
        UserEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (("admin").equals(userId)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getUserId(), userEntity.getPassword(), authorities);
    }
    
    // 회원가입 시 발생할 수 있는 오류들 관련
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        
        return validatorResult;
    }
    
    // 비밀번호 변경
//    public void updatePassword(UserEntity userEntity, String newPassword) {
//    	userEntity.setPassword(passwordEncoder.encode(newPassword));
//    	userRepository.save(userEntity); // detached인 상태인 객체(Account)를 명시적으로 merge
//    }
    
    // 이메일로 회원 번호(PK) 찾기
	public String findIdByUserId(String userId) {
		return userRepository.findIdByUserId(userId);
	}
    
	// 중복 아이디 찾기
	public Long countByUserId(String userId) {
	    return userRepository.countByUserId(userId);
	}
	
	// 중복 닉네임 찾기
	public Long countByNickname(String nickname) {
	    return userRepository.countByNickname(nickname);
	}
	
}
