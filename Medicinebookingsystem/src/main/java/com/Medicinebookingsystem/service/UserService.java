package com.Medicinebookingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.User;
@Service
public interface UserService {
	public User createUser(User user);
	//public List<Booking> getBookingsByUserId(int userId) ;
	public abstract  User updateUser(int userId,User user);
	public abstract String loginUser(String userName,String password);
	public User getUserById(int userId);
}
