package com.Medicinebookingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.service.MedicineService;


@RestController
public class MedicineController {
	@Autowired
	private MedicineService medicineService;
	//@PostMapping(value = "createmedicine")
	/*
	 * public Medicine createMedicine(@RequestBody Medicine medicine) { return
	 * medicineService.createMedicine(medicine); }
	 */
	
	//@PutMapping(value="/updateMedicine{medicineId}")
	/*
	 * public ResponseEntity<Medicine> updateUser(@PathVariable("medicineId") int
	 * medicineId,Medicine medicine){ return new
	 * ResponseEntity<Medicine>(medicineService.updateMedicine(medicineId,
	 * medicine), HttpStatus.FOUND); }
	 */
	 
	//@GetMapping(value="getAllMedicines")
	/*
	 * public List<Medicine> getAllMedicines(){ return medicineService.findAll(); }
	 */
	
	//@GetMapping(value="searchByMedicineName/{medicineName}")
	public List<Medicine> searchByMedicineName(@PathVariable String medicineName){
		return medicineService.findByName(medicineName);
	}
}
