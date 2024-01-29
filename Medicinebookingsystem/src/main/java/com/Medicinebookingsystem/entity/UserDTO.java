package com.Medicinebookingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	 private int userId;
	    private String userName;
	    private String email;
	    // Other user-related fields

	    public static UserDTO fromUser(User user) {
	        UserDTO userDTO = new UserDTO();
	        userDTO.setUserId(user.getUserId());
	        userDTO.setUserName(user.getUserName());
	        userDTO.setEmail(user.getUserEmail());
	        // Set other user-related fields

	        return userDTO;
	    }
}
