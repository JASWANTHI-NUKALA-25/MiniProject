package com.Medicinebookingsystem.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.User;
import com.Medicinebookingsystem.exception.EmailAlreadyExistsException;
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
	@org.springframework.transaction.annotation.Transactional                                                                                                                                                  
	public User updateUser(int userId, User user) {
		User tempUser=null;
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			existingUser.setUserName(user.getUserName());
			existingUser.setUserWalletBalance(user.getUserWalletBalance());
			existingUser.setUserMobileNumber(user.getUserMobileNumber());
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
	public User loginUser(String userEmail, String password) {
		return userRepository.findByUserEmailAndUserPassword(userEmail, password);
//		if(user!=null && user.getUserName().equals(userName)&&user.getUserPassword().equals(password)) {
//			return user;
//		}
//		else {
//			throw new UserNotFoundException("Wrong user name and password");
//		}

		
//	    if (existingUser != null && existingUser.getUserPassword().equals(password)) {
//	        // Successfully logged in, return a success message
//	        return "Login successful!";
//	    } else {
//	        // Wrong credentials, throw an exception
//	        throw new UserNotFoundException("Wrong user name and password");
//	    }
	}
	@Override
	public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
	@Transactional
    public void updateUserWalletBalance(int userId, double walletBalance) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        double currentBalance = user.getUserWalletBalance();
        double updatedBalance = currentBalance + walletBalance;

        // Set the updated balance and save the user
        user.setUserWalletBalance(updatedBalance);
        userRepository.save(user);
        
    }
	public User updateUserPassword(String userEmail, String newPassword) {
        User user = userRepository.findByUserEmail(userEmail);
        
        if (user != null) {
            user.setUserPassword(newPassword);
            return userRepository.save(user);
        } else {
            // Handle the case when the user is not found
            throw new UserNotFoundException("User not found for email: " + userEmail);
        }
    }
}


