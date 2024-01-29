package com.Medicinebookingsystem.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.Medicine;
import com.Medicinebookingsystem.exception.AdminNotFoundException;
import com.Medicinebookingsystem.exception.MedicineNotFoundException;
import com.Medicinebookingsystem.exception.UserNotFoundException;
import com.Medicinebookingsystem.repository.AdminRepository;
import com.Medicinebookingsystem.repository.MedicineRepository;
import com.Medicinebookingsystem.repository.MedicineRepository2;

@Service
public class MedicineServiceImpl implements MedicineService{
	@Autowired
    private MedicineRepository medicineRepository;
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private MedicineRepository2 medicineRepository2;
	
	@Autowired
	private Medicine medicine;

    public List<Medicine> createMedicine(List<Medicine> medicines,int adminId) {
    	 Admin admin = adminRepository.findById(adminId)
  	            .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));
    	 for (Medicine medicine : medicines) {
    		 medicine.setAdmin(admin);
		}
    	// medicine.setAdmin(admin);
        return  medicineRepository.saveAll(medicines);
    }

	@Override
	public Medicine updateMedicine(int medicineId, Medicine medicine,int adminId) {
		 Admin admin = adminRepository.findById(adminId)
	  	            .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));
		
		Medicine tempMedicine=null;
		Optional<Medicine> optionalMedicine = medicineRepository.findById(medicineId);
		if(optionalMedicine.isPresent()) {
			Medicine existingMedicine = optionalMedicine.get();
			existingMedicine.setMedicineName(medicine.getMedicineName());
			existingMedicine.setMedicineQuantity(medicine.getMedicineQuantity());
			existingMedicine.setMedicinePrice(medicine.getMedicinePrice());
			tempMedicine= medicineRepository.save(existingMedicine);
		}
		else {
			throw new MedicineNotFoundException("Medicine Id not found");
		}
		return tempMedicine;
	}

	@Override
	public List<Medicine> getMedicinesByUserId(int userId) {
		
		return null;
	}

	@Override
	public List<Medicine> findAll() {
		return medicineRepository.findAll();
		
	}
	//Admin admin = adminRepository.findById(adminId)
    //  .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));
//medicine.setAdmin(admin);
	@Override
	public List<Medicine> findByName(String medicineName) {
		List<Medicine> list = medicineRepository2.findByMedicineName(medicineName);
	    if (list.isEmpty()) {
	        throw new MedicineNotFoundException(medicineName + " is not available!");
	    }
	    return list;
	}

	@Override
	public void deleteMedicine(int medicineId) {
		 Optional<Medicine> optionalMedicine = medicineRepository.findById(medicineId);
		  if (optionalMedicine.isPresent()) {
		    medicineRepository.delete(optionalMedicine.get());
		  } else {
		    throw new MedicineNotFoundException("Medicine not found");
		  }
		
	}

	@Override
	 public Optional<Medicine> getMedicineById(int medicineId) {
        return medicineRepository.findById(medicineId);
    }

	
} 
    

