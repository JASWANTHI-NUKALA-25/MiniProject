package com.Medicinebookingsystem.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.User;
@Service
public interface UserService {
	public User createUser(User user);
	//public List<Booking> getBookingsByUserId(int userId) ;
	@org.springframework.transaction.annotation.Transactional
	public abstract  User updateUser(int userId,User user);
	public abstract User loginUser(String userEmail,String password);
	public User getUserById(int userId);
	public void updateUserWalletBalance(int userId, double walletBalance);
	 public User updateUserPassword(String userEmail, String newPassword);
}
