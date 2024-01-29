package com.Medicinebookingsystem.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDTO {
	private String userName;
    private List<MedicineBookingDTO> medicineBookings;
    private Double totalPrice;
    private Date bookingDate;
    private int bookingId;
}
