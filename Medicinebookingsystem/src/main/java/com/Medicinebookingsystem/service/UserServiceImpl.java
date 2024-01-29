package com.Medicinebookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.User;
import com.Medicinebookingsystem.exception.AdminNotFoundException;
import com.Medicinebookingsystem.exception.EmailAlreadyExistsException;
import com.Medicinebookingsystem.exception.MedicineNotFoundException;
import com.Medicinebookingsystem.exception.UserNotFoundException;
import com.Medicinebookingsystem.repository.BookingRepository;
import com.Medicinebookingsystem.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Override
	public User createUser(User user) {
		 // Check if email is already in use
        User existingUser = userRepository.findByUserEmail(user.getUserEmail());
        if (existingUser != null) {
            throw new EmailAlreadyExistsException("Email already exists. Please use a different email address.");
        }

        // Check if user id is null
		/*
		 * if (user.getUserId() ==0) { throw new
		 * RuntimeException("User ID cannot be null."); }
		 */

        // Save the user if validations pass
        return userRepository.save(user);
	}
	@Override
	public User updateUser(int userId, User user) {
		User tempUser=null;
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			existingUser.setUserName(user.getUserName());
			existingUser.setUserWalletBalance(user.getUserWalletBalance());
			existingUser.setUserEmail(user.getUserEmail());
			existingUser.setUserAddressLine1(user.getUserAddressLine1());
			existingUser.setUserAddressLine2(user.getUserAddressLine2());
			tempUser= userRepository.save(existingUser);
		}
		else {
			throw new UserNotFoundException("User not found");
		}
		return tempUser;
	}
	@Override
	public String loginUser(String userName, String password) {
		User existingUser = userRepository.findByUserName(userName);

	    if (existingUser != null && existingUser.getUserPassword().equals(password)) {
	        // Successfully logged in, return a success message
	        return "Login successful!";
	    } else {
	        // Wrong credentials, throw an exception
	        throw new UserNotFoundException("Wrong user name and password");
	    }
	}
	@Override
	public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
}


