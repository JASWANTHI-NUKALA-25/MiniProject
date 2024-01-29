package com.Medicinebookingsystem.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.BookingDTO;
import com.Medicinebookingsystem.entity.BookingRequestDTO;
import com.Medicinebookingsystem.entity.ErrorResponse;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.exception.BookingNotFoundException;
import com.Medicinebookingsystem.exception.InsufficientMedicineQuantityException;
import com.Medicinebookingsystem.exception.InsufficientWalletBalanceException;
import com.Medicinebookingsystem.exception.MedicineNotFoundException;
import com.Medicinebookingsystem.exception.UserNotFoundException;
import com.Medicinebookingsystem.service.BookingService;


@RestController
public class BookingController {
	@Autowired
	private BookingService bookingService;

	    
	    @PostMapping("/bookMedicines")
	    public ResponseEntity<BookingDTO> bookMedicines(@RequestBody BookingRequestDTO bookingRequest) {
	            BookingDTO bookingDTO = bookingService.bookMedicines(bookingRequest.getUserId(), bookingRequest.getMedicineQuantities());
	            return ResponseEntity.ok(bookingDTO);
	        
	    }
	    @DeleteMapping("/cancel/{bookingId}")
	    public ResponseEntity<String> cancelBooking(@PathVariable int bookingId) {
	        try {
	            bookingService.cancelBooking(bookingId);
	            return ResponseEntity.ok("Booking with ID " + bookingId + " has been canceled.");
	        } catch (BookingNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while canceling the booking.");
	        }
	    }
	  
	    


}
