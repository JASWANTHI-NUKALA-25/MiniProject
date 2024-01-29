package com.Medicinebookingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.BookingDTO;
import com.Medicinebookingsystem.entity.BookingDetailsDTO;
import com.Medicinebookingsystem.entity.BookingRequestDTO;
import com.Medicinebookingsystem.exception.BookingNotFoundException;
import com.Medicinebookingsystem.service.BookingService;


@RestController
@CrossOrigin("http://localhost:2000/")
public class BookingController {
	@Autowired
	private BookingService bookingService;

	    
	    @PostMapping("/bookMedicines")
	    public ResponseEntity<BookingDTO> bookMedicines(@RequestBody BookingRequestDTO bookingRequest) {
	            BookingDTO bookingDTO = bookingService.bookMedicines(bookingRequest.getUserId(), bookingRequest.getMedicineQuantities());
	            return ResponseEntity.ok(bookingDTO);   
	    }
	    @DeleteMapping("/cancel/{bookingId}/{medicineId}")
	    public ResponseEntity<String> cancelBooking(@PathVariable int bookingId,@PathVariable int medicineId) throws BookingNotFoundException {
	        bookingService.cancelBooking(bookingId, medicineId);
	        return ResponseEntity.ok("Booking with ID " + bookingId + " has been canceled.");
	    }
	  
	    @GetMapping("/user/{userId}")
	    public List<BookingDetailsDTO> getBookingDetailsByUserId(@PathVariable int userId) {
	        return bookingService.getBookingDetailsByUserId(userId);
	    }


}
