package com.Medicinebookingsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.BookedDetails;
import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.BookingDTO;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.MedicineQuantityDTO;
import com.Medicinebookingsystem.entity.User;
import com.Medicinebookingsystem.entity.UserBookedDTO;
import com.Medicinebookingsystem.entity.UserDTO;
import com.Medicinebookingsystem.exception.BookingNotFoundException;
import com.Medicinebookingsystem.exception.InsufficientMedicineQuantityException;
import com.Medicinebookingsystem.exception.InsufficientWalletBalanceException;
import com.Medicinebookingsystem.exception.MedicineNotFoundException;
import com.Medicinebookingsystem.exception.UserNotFoundException;
import com.Medicinebookingsystem.repository.BookingRepository;
import com.Medicinebookingsystem.repository.MedicineRepository;
import com.Medicinebookingsystem.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService{
	@Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Override
    public BookingDTO bookMedicines(int userId, List<MedicineQuantityDTO> medicineQuantities){
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

	    Booking booking = new Booking(user);
	    double totalMedicinePrice = 0.0;

	    for (MedicineQuantityDTO dto : medicineQuantities) {
	        int medicineId = dto.getMedicineId();
	        int quantity = dto.getMedicineQuantity();

	        Medicine medicine = medicineRepository.findById(medicineId)
	                .orElseThrow(() -> new MedicineNotFoundException("Medicine with ID " + medicineId + " not found"));

	        if (medicine.getMedicineQuantity() < quantity) {
	            throw new InsufficientMedicineQuantityException("Insufficient quantity of medicine available");
	        }

	        BookedDetails bookingMedicine = new BookedDetails();
	        bookingMedicine.setBooking(booking);
	        bookingMedicine.setMedicine(medicine);
	        bookingMedicine.setMedicineQuantity(quantity);
	        
	        booking.getBookedMedicines().add(bookingMedicine);

	        double medicinePrice = medicine.getMedicinePrice();
	        totalMedicinePrice += (medicinePrice * quantity);
	        booking.setTotalPriceOfBookedMedicines(totalMedicinePrice);
	    }

	    // Save the booking entity here
	    bookingRepository.save(booking);

	    // Update medicine quantities and save the user
	    for (MedicineQuantityDTO dto : medicineQuantities) {
	        int medicineId = dto.getMedicineId();
	        int quantity = dto.getMedicineQuantity();
	        Medicine medicine = medicineRepository.findById(medicineId)
	                .orElseThrow(() -> new MedicineNotFoundException("Medicine with ID " + medicineId + " not found"));
	        medicine.setMedicineQuantity(medicine.getMedicineQuantity() - quantity);
	    }
	    if (user.getUserWalletBalance() < totalMedicinePrice) {
	        throw new InsufficientWalletBalanceException("Insufficient wallet balance");
	    }

	    // Deduct totalMedicinePrice from the user's wallet
	    user.setUserWalletBalance(user.getUserWalletBalance() - totalMedicinePrice);
	    
	    // Save the updated user
	    userRepository.save(user);
	  

	    BookingDTO bookingDTO = new BookingDTO();
	    
	    // Set properties of the bookingDTO
	    bookingDTO.setBookingId(booking.getBookingId());
	    bookingDTO.setBookingDate(booking.getBookingDate());
	    bookingDTO.setUser(UserDTO.fromUser(user));
	    bookingDTO.setTotalPrice(totalMedicinePrice);
	    // Create a list of booked medicines from bookedMedicines in the booking entity
	    List<MedicineQuantityDTO> bookedMedicines = new ArrayList<>();
	    for (BookedDetails bookingMedicine : booking.getBookedMedicines()) {
	        MedicineQuantityDTO medicineQuantityDTO = new MedicineQuantityDTO();
	        medicineQuantityDTO.setMedicineId(bookingMedicine.getMedicine().getMedicineId());
	        medicineQuantityDTO.setMedicineQuantity(bookingMedicine.getMedicineQuantity());
	  
	        bookedMedicines.add(medicineQuantityDTO);
	    }
	    bookingDTO.setBookedMedicines(bookedMedicines);
	    bookingDTO.setTotalPrice(totalMedicinePrice);
	    return bookingDTO;
    }
@Override
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));

        // Refund the user
        User user = booking.getUser();
        double totalMedicinePrice = calculateTotalMedicinePrice(booking.getBookedMedicines());
        user.setUserWalletBalance(user.getUserWalletBalance() + totalMedicinePrice);

        // Update medicine quantities
        for (BookedDetails bookedMedicine : booking.getBookedMedicines()) {
            Medicine medicine = bookedMedicine.getMedicine();
            int quantity = bookedMedicine.getMedicineQuantity();
            medicine.setMedicineQuantity(medicine.getMedicineQuantity() + quantity);
        }

        // Remove the booking
        bookingRepository.delete(booking);
    }

    private double calculateTotalMedicinePrice(List<BookedDetails> bookedMedicines) {
        double totalMedicinePrice = 0.0;
        for (BookedDetails bookedMedicine : bookedMedicines) {
            Medicine medicine = bookedMedicine.getMedicine();
            int quantity = bookedMedicine.getMedicineQuantity();
            totalMedicinePrice += medicine.getMedicinePrice() * quantity;
        }
        return totalMedicinePrice;
    }
    @Override
    public List<UserBookedDTO> getBookedDetailsByUserId(int userId) {
    	 User user = userRepository.findById(userId)
 	            .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        List<Booking> bookings = bookingRepository.findByUserUserId(userId);
        		

        List<UserBookedDTO> bookingDTOs = new ArrayList<>();

 

        for (Booking booking : bookings) {

        	UserBookedDTO bookingDTO = new UserBookedDTO();

            bookingDTO.setBookingId(booking.getBookingId());

            bookingDTO.setBookingDate(booking.getBookingDate());

 

            List<MedicineQuantityDTO> bookedMedicines = new ArrayList<>();

            for (BookedDetails bookedDetails : booking.getBookedMedicines()) {

                MedicineQuantityDTO medicineQuantityDTO = new MedicineQuantityDTO();

                medicineQuantityDTO.setMedicineId(bookedDetails.getMedicine().getMedicineId());

                medicineQuantityDTO.setMedicineQuantity(bookedDetails.getMedicineQuantity());

               

                // You can set other properties of the medicineQuantityDTO as needed

 

                bookedMedicines.add(medicineQuantityDTO);

            }

            bookingDTO.setBookedMedicines(bookedMedicines);

            bookingDTO.setTotalPrice(booking.getTotalPriceOfBookedMedicines());

            //bookingDTO.setTotalPrice(calculateTotalPrice(booking.getBookedMedicines())); // Implement calculateTotalPrice() method

 

            bookingDTOs.add(bookingDTO);

        }

 

        return bookingDTOs;

    }
   
}
