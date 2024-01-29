package com.Medicinebookingsystem.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userId;

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String userPassword;

	
	  @NotNull(message = "Mobile number is required")
	  @Min(value = 1000000000, message ="Mobile number must be at least 10 digits")
	  @Max(value = 9999999999L, message = "Mobile number cannot exceed 10 digits")
	  @Schema(example = "0")
	  private Long userMobileNumber;
	 
    @Max(value=1000,message="wallet balance limit upto 1000")
    private Double userWalletBalance;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
    
	
	  @OneToMany(mappedBy = "user")
	  @JsonIgnore
	  private List<Booking> bookings;
	  @NotBlank(message = "Address is required")
	
	  private String userAddressLine1;
	  private String userAddressLine2;
	

}
