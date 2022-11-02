package com.showmual.entity.user;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByUserId(String userId);
	
	boolean existsByUserId(String userId);
	
	@Query(value = "select id from users_tbl where userId = :userId", nativeQuery = true)
	String findIdByUserId(String userId);
	
	//@Query(value = "select count(userId) from users_tbl where userId = :userId", nativeQuery = true)
	Long countByUserId(String userId);
	
	//@Query(value = "select count(nickname) from users_tbl where nickname = :nickname", nativeQuery = true)
	Long countByNickname(String nickname);
}
