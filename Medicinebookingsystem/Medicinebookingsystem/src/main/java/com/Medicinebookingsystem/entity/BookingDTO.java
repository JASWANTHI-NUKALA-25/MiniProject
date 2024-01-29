package com.Medicinebookingsystem.entity;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
	 private int bookingId;
	    private Date bookingDate;
	    private UserDTO user; // Change the type to UserDTO
//	    private List<MedicineQuantityDTO> bookedMedicines;
	    private List<MedicineQuantityDTO> bookedMedicines;
	    private double totalPrice;
	    // Constructors, getters, and setters...
	    
	    // Setter for UserDTO
	    public void setUser(UserDTO user) {
	        this.user = user;
	    }
	    
	    // Getter for UserDTO
	    public UserDTO getUser() {
	        return user;
	    }
}
