package com.Medicinebookingsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.BookedDetails;
import com.Medicinebookingsystem.entity.Booking;
import com.Medicinebookingsystem.entity.BookingDTO;
import com.Medicinebookingsystem.entity.BookingDetailsDTO;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.MedicineBookingDTO;
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
	        //int medicineId = dto.getMedicineId();
	    	String medicineName=dto.getMedicineName();
	        int quantity = dto.getMedicineQuantity();

	        Medicine medicine = medicineRepository.findByMedicineName(medicineName);
	                //.orElseThrow(() -> new MedicineNotFoundException("Medicine with Name " + medicineName + " not found"));

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
	        //int medicineId = dto.getMedicineId();
	    	String medicineName=dto.getMedicineName();
	        int quantity = dto.getMedicineQuantity();
	        Medicine medicine = medicineRepository.findByMedicineName(medicineName);
	                //.orElseThrow(() -> new MedicineNotFoundException("Medicine with ID " + medicineId + " not found"));
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
	       // medicineQuantityDTO.setMedicineId(bookingMedicine.getMedicine().getMedicineId());
	        medicineQuantityDTO.setMedicineName(bookingMedicine.getMedicine().getMedicineName());
	        medicineQuantityDTO.setMedicineQuantity(bookingMedicine.getMedicineQuantity());
	  
	        bookedMedicines.add(medicineQuantityDTO);
	    }
	    bookingDTO.setBookedMedicines(bookedMedicines);
	    bookingDTO.setTotalPrice(totalMedicinePrice);
	    return bookingDTO;
    }

//@Transactional
//    public void cancelBooking(int bookingId) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
//
//        // Refund the user
//        User user = booking.getUser();
//        double totalMedicinePrice = calculateTotalMedicinePrice(booking.getBookedMedicines());
//        user.setUserWalletBalance(user.getUserWalletBalance() + totalMedicinePrice);
//
//        // Update medicine quantities
//        for (BookedDetails bookedMedicine : booking.getBookedMedicines()) {
//            Medicine medicine = bookedMedicine.getMedicine();
//            int quantity = bookedMedicine.getMedicineQuantity();
//            medicine.setMedicineQuantity(medicine.getMedicineQuantity() + quantity);
//        }
//
//        // Remove the booking
//        bookingRepository.delete(booking);
//    }

//@Transactional
//public void cancelBooking(int bookingId, String medicineName) {
//    // Retrieve the booking from the repository
//    Booking booking = bookingRepository.findByBookingIdAndMedicineName(bookingId, medicineName);
////            .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
//
//    bookingRepository.delete(booking);
//   
//    bookingRepository.save(booking);
//}
@Override
public void cancelBooking(int bookingId, int medicineId) {
    Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
    if (optionalBooking.isPresent()) {
        Booking booking = optionalBooking.get();

        // Find the booked medicine in the booking
        BookedDetails bookedDetailsToRemove = null;
        for (BookedDetails bookedDetails : booking.getBookedMedicines()) {
            if (bookedDetails.getMedicine().getMedicineId() == medicineId) {
                bookedDetailsToRemove = bookedDetails;
                break;
            }
        }

        if (bookedDetailsToRemove != null) {
            // Remove the booked medicine from the booking
            booking.getBookedMedicines().remove(bookedDetailsToRemove);

            // Optionally, you can update any other relevant fields or perform additional logic

            // Save the updated booking
            bookingRepository.save(booking);
        } else {
            throw new MedicineNotFoundException("Booked medicine with ID " + medicineId + " not found in the booking");
        }
    } else {
        throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
    }
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
            bookingDTO.setUserName(booking.getUser().getUserName());

 

            List<MedicineQuantityDTO> bookedMedicines = new ArrayList<>();

            for (BookedDetails bookedDetails : booking.getBookedMedicines()) {

                MedicineQuantityDTO medicineQuantityDTO = new MedicineQuantityDTO();

            //    medicineQuantityDTO.setMedicineId(bookedDetails.getMedicine().getMedicineId());

                medicineQuantityDTO.setMedicineQuantity(bookedDetails.getMedicineQuantity());
                medicineQuantityDTO.setMedicineName(bookedDetails.getMedicine().getMedicineName());
               
               

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
    public List<BookingDetailsDTO> getBookingDetailsByUserId(int userId) {
        List<Booking> bookings = bookingRepository.findByUserUserId(userId);

        return bookings.stream()
                .map(this::mapToBookingDetailsDTO)
                .collect(Collectors.toList());
    }

    private BookingDetailsDTO mapToBookingDetailsDTO(Booking booking) {
        BookingDetailsDTO dto = new BookingDetailsDTO();
        dto.setUserName(booking.getUser().getUserName());
        dto.setTotalPrice(booking.getTotalPriceOfBookedMedicines());
        dto.setBookingId(booking.getBookingId());
        dto.setBookingDate(booking.getBookingDate());

        List<MedicineBookingDTO> medicineBookings = booking.getBookedMedicines().stream()
                .map(this::mapToMedicineBookingDTO)
                .collect(Collectors.toList());

        dto.setMedicineBookings(medicineBookings);

        return dto;
    }

    private MedicineBookingDTO mapToMedicineBookingDTO(BookedDetails bookedDetails) {
        MedicineBookingDTO dto = new MedicineBookingDTO();
        dto.setMedicineName(bookedDetails.getMedicine().getMedicineName());
        dto.setQuantity(bookedDetails.getMedicineQuantity());
        dto.setIndividualPrice((double) (bookedDetails.getMedicine().getMedicinePrice() * bookedDetails.getMedicineQuantity()));

        return dto;
    }

   
}
