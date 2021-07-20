package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>{
	List<User> findTop3ByOrderByIdDesc();
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	 User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.reset_password_token = ?1")
	public User findByResetPasswordToken(String token);
	
	@Query("SELECT u FROM User u WHERE u.email = :name")
    public User getUserByUsername(@Param("name") String username);
//	User findFirstByOrderByLastnameAsc();

//	User findTopByOrderByAgeDesc();
//
//	Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);
//
//	Slice<User> findTop3ByLastname(String lastname, Pageable pageable);
//
//	List<User> findFirst10ByLastname(String lastname, Sort sort);
//
//	List<User> findTop10ByLastname(String lastname, Pageable pageable);
}
