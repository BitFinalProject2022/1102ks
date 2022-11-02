package com.showmual.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.showmual.entity.user.UserRepository;
import com.showmual.model.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckUserIdValidator extends AbstractValidator<UserDto> {

	private final UserRepository userRepository;

	@Override
	protected void doValidate(UserDto userDto, Errors errors) {
		if (userRepository.existsByUserId(userDto.toEntity().getUserId())) {
			errors.rejectValue("userId", "아이디 중복 오류", "이미 사용중인 아이디입니다.");
		}
	}

}
