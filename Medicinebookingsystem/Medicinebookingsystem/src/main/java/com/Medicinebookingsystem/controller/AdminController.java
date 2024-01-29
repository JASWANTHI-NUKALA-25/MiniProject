package com.Medicinebookingsystem.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.exception.AdminNotFoundException;
import com.Medicinebookingsystem.service.AdminService;
import com.Medicinebookingsystem.service.MedicineService;

@RestController
@Validated
@CrossOrigin("http://localhost:2000/")
public class AdminController {
	
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MedicineService medicineService;
	
	@PostMapping(value="Admin/signup")
	public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
		return new ResponseEntity<Admin>(adminService.createAdmin(admin), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/login/{adminName}/{password}")
	public ResponseEntity<String> loginAdmin(@PathVariable String adminName,@PathVariable String password) throws AdminNotFoundException {
		return new ResponseEntity(adminService.loginAdmin(adminName, password),HttpStatus.OK);
	}
	
	@PostMapping(value = "createmedicines/{adminId}")
    public List<Medicine> createMedicine(@RequestBody List<Medicine> medicines,@PathVariable Integer adminId) {
        return medicineService.createMedicine(medicines,adminId);
    }
	
	@PutMapping(value="/updateMedicine/{adminId}/{medicineId}")
	public ResponseEntity<Medicine> updateUser(@PathVariable("medicineId") int medicineId,@RequestBody Medicine medicine,@PathVariable int adminId){
		return new ResponseEntity<Medicine>(medicineService.updateMedicine(medicineId, medicine,adminId), HttpStatus.FOUND);
	}
	@CrossOrigin(origins = "http://localhost:2000/")
	@DeleteMapping(value = "/deleteMedicine/{medicineId}")
	public ResponseEntity<Void> deleteMedicine(@PathVariable("medicineId") int medicineId) {
	  try {
	    medicineService.deleteMedicine(medicineId);
	    return ResponseEntity.noContent().build();
	  } catch (Exception e) {
	    // Handle exception, return an error response, etc.
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	  }
	}
	@GetMapping(value="getAllMedicines")
	public List<Medicine> getAllMedicines(){
		return medicineService.findAll();
	}
	@GetMapping("medicines/{medicineId}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable int medicineId) {
        Optional<Medicine> medicine = medicineService.getMedicineById(medicineId);

        return medicine.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@GetMapping(value="searchByMedicineName/{medicineName}")
	public List<Medicine> searchByMedicineName(@PathVariable String medicineName){
		return medicineService.findByName(medicineName);
	}
}
