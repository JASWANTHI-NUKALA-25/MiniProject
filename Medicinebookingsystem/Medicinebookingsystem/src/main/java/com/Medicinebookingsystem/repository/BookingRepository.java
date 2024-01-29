package com.Medicinebookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.User;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	public List<Booking> findByUser(User user);
	
	// List<Booking> findById(int userId);
	
	public List<Booking> findByUserUserId(int userId);
	
//	public Booking findByBookingIdAndMedicineName(int bookingId,String medicineName);
}
