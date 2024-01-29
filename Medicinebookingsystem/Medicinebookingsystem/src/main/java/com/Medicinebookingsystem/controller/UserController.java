package com.Medicinebookingsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.User;
import com.Medicinebookingsystem.entity.UserBookedDTO;
import com.Medicinebookingsystem.exception.AdminNotFoundException;
import com.Medicinebookingsystem.repository.UserRepository;
import com.Medicinebookingsystem.service.AdminService;
import com.Medicinebookingsystem.service.BookingService;
import com.Medicinebookingsystem.service.MedicineService;
import com.Medicinebookingsystem.service.UserService;

@RestController
@RequestMapping(value = "user")
@Validated
@CrossOrigin(origins = "http://localhost:2000/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/signup")
	public ResponseEntity<User> loginUser(@Valid @RequestBody User user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED); 
	}
    
    @PutMapping(value="/updateUser/{UserId}")
	public ResponseEntity<User> updateUser(@PathVariable("UserId") int userId,@RequestBody User user){
		return new ResponseEntity<User>(userService.updateUser(userId, user), HttpStatus.OK);
	}
    @GetMapping("/login/{userEmail}/{password}")
	public User loginUser(@PathVariable String userEmail,@PathVariable String password) {
		return userService.loginUser(userEmail, password);
	}
    @GetMapping(value="getAllMedicines")
	public List<Medicine> getAllMedicines(){
		return medicineService.findAll();
	}
    @GetMapping(value="searchByMedicineName/{medicineName}")
	public List<Medicine> searchByMedicineName(@PathVariable String medicineName){
		return medicineService.findByName(medicineName);
	}
    @GetMapping("/bookedDetails/{userId}")
    public List<UserBookedDTO> getBookedDetailsByUserId(@PathVariable int userId) {
        return bookingService.getBookedDetailsByUserId(userId);
    }
    @PutMapping("/{userId}/{walletBalance}")
    public ResponseEntity<String> updateWalletBalance(
            @PathVariable int userId,
            @PathVariable double walletBalance) {
        
        	userService.updateUserWalletBalance(userId, walletBalance);
            return new ResponseEntity<>("Wallet balance updated successfully", HttpStatus.OK);
        
    }
    
    @GetMapping("/login2/{userEmail}/{password}")
    public ResponseEntity<?> loginUser2(@PathVariable String userEmail, @PathVariable String password) {
        User user = userService.loginUser(userEmail, password);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        // Check if it's an admin login
        Admin admin = adminService.loginadmin(userEmail, password);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            // Admin not found, return an error response
            return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
        }
    }

    
    @PutMapping("/updatePassword/{userEmail}")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable String userEmail,
            @RequestParam String newPassword) {
        
        User updatedUser = userService.updateUserPassword(userEmail, newPassword);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
