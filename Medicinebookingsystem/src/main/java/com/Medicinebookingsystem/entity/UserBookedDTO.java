package com.Medicinebookingsystem.entity;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookedDTO {
	private int bookingId;

    private Date bookingDate;

    private List<MedicineQuantityDTO> bookedMedicines;

    private double totalPrice;
}
