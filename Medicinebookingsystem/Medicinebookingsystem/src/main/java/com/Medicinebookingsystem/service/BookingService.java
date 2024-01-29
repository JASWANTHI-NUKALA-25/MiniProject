package com.Medicinebookingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.BookedDetails;
import com.Medicinebookingsystem.entity.BookingDTO;
import com.Medicinebookingsystem.entity.BookingDetailsDTO;
import com.Medicinebookingsystem.entity.MedicineQuantityDTO;
import com.Medicinebookingsystem.entity.UserBookedDTO;
@Service
public interface BookingService {
	
	
	public BookingDTO bookMedicines(int userId, List<MedicineQuantityDTO> medicineQuantities)  ;
//	 public void cancelBooking(int bookingId);
	 public List<UserBookedDTO> getBookedDetailsByUserId(int userId);
	public List<BookingDetailsDTO> getBookingDetailsByUserId(int userId);
//	void cancelBooking(int bookingId, String medicineName);
	void cancelBooking(int bookingId, int medicineId);
}
