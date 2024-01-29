package com.Medicinebookingsystem.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
	private int userId;
    private List<MedicineQuantityDTO> medicineQuantities;
//	private List<Medicine> medicineQuantities;
}
