package com.Medicinebookingsystem.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MedicineQuantityDTO {
	 private   int medicineId;
	    private int medicineQuantity;
}
