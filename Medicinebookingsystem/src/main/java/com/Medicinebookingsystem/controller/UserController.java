package com.Medicinebookingsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.entity.User;
import com.Medicinebookingsystem.entity.UserBookedDTO;
import com.Medicinebookingsystem.service.BookingService;
import com.Medicinebookingsystem.service.MedicineService;
import com.Medicinebookingsystem.service.UserService;

@RestController
@RequestMapping(value = "user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private BookingService bookingService;

    @PostMapping(value="/signup")
	public ResponseEntity<User> loginUser(@Valid @RequestBody User user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED); 
	}
    
    @PutMapping(value="/updateUser{UserId}")
	public ResponseEntity<User> updateUser(@PathVariable("UserId") int userId,User user){
		return new ResponseEntity<User>(userService.updateUser(userId, user), HttpStatus.FOUND);
	}
    @GetMapping("/login/{userName}/{password}")
	public ResponseEntity<String> loginUser(@PathVariable String userName,@PathVariable String password) {
		return new ResponseEntity(userService.loginUser(userName, password),HttpStatus.OK);
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
}
