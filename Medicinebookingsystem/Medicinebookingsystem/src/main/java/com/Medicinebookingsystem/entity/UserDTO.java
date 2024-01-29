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
	    private String userEmail;
	    private Double userWalletBalance=0.0;
	    private String userAddressLine1;
		  private String userAddressLine2;
		  private Long userMobileNumber;
	    // Other user-related fields

	    public static UserDTO fromUser(User user) {
	        UserDTO userDTO = new UserDTO();
	        userDTO.setUserId(user.getUserId());
	        userDTO.setUserName(user.getUserName());
	        userDTO.setUserEmail(user.getUserEmail());
	        userDTO.setUserWalletBalance(user.getUserWalletBalance());
	        userDTO.setUserMobileNumber(user.getUserMobileNumber());
	        userDTO.setUserAddressLine1(user.getUserAddressLine1());
	        userDTO.setUserAddressLine2(user.getUserAddressLine2());
	        
	        // Set other user-related fields

	        return userDTO;
	    }
}
