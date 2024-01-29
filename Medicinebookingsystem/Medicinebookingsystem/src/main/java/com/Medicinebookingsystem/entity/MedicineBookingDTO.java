package com.Medicinebookingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineBookingDTO {
	 private String medicineName;
	    private int quantity;
	    private Double individualPrice;
}
