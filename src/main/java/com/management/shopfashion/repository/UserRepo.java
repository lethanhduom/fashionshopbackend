package com.management.shopfashion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	public User findByEmail(String email);
	@Query("UPDATE User SET status=1 where id_user=?1 and verificationCode=?2")
	public User AcitveAccount(int id,String code);
	@Query("Select u from User u where u.username=?1")
	public User getUser(String userName);
	@Query("Select c from User c where role= 'admin' ")
	public User getAccountUser();
	public Optional<User>findByRole(String role);
	@Query("Select u from User u where role='user'")
	public Page<User>getUserAccounts(Pageable pageable);
	@Query("SELECT u FROM User u WHERE u.role = 'user' AND " +
		       "(LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
		Page<User> searchUserAccounts(@Param("keyword") String keyword, Pageable pageable);


}
