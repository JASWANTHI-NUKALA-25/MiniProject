package com.Medicinebookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public List<Booking> findAllByUserId(int uerId);

	public User findByUserName(String userName);

	public User findByUserEmail(String email);
	
	

}
