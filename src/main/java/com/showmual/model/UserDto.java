package com.showmual.model;

import javax.validation.constraints.NotBlank;

import com.showmual.entity.user.UserEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
	
	private Long id;
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;
	
    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .build();
    }
    
    @Builder
    public UserDto(Long id, String userId, String password, String nickname) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
	
}
