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
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.exception.AdminNotFoundException;
import com.Medicinebookingsystem.service.AdminService;
import com.Medicinebookingsystem.service.MedicineService;

@RestController
@Validated
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
    public List<Medicine> createMedicine(@RequestBody List<Medicine> medicines,int adminId) {
        return medicineService.createMedicine(medicines,adminId);
    }
	
	@PutMapping(value="/updateMedicine/{adminId}/{medicineId}")
	public ResponseEntity<Medicine> updateUser(@PathVariable("medicineId") int medicineId,Medicine medicine,@PathVariable int adminId){
		return new ResponseEntity<Medicine>(medicineService.updateMedicine(medicineId, medicine,adminId), HttpStatus.FOUND);
	}
	 
	@GetMapping(value="getAllMedicines/{adminId}")
	public List<Medicine> getAllMedicines(int adminId){
		return medicineService.findAll(adminId);
	}
	
	@GetMapping(value="searchByMedicineName/{medicineName}")
	public List<Medicine> searchByMedicineName(@PathVariable String medicineName){
		return medicineService.findByName(medicineName);
	}
}
