package com.Medicinebookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.entity.User;
import com.Medicinebookingsystem.exception.AdminNotFoundException;
import com.Medicinebookingsystem.exception.UserNotFoundException;
import com.Medicinebookingsystem.repository.AdminRepository;
@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin createAdmin(Admin admin) {
		Admin newAdmin=null;
		if(admin.getAdminId()!=0) {
			newAdmin=adminRepository.save(admin);
		}
		else {
			
		}
		return newAdmin;
	}

	@Override
	public String loginAdmin(String adminName, String password) throws AdminNotFoundException {
		Admin existingAdmin = adminRepository.findByAdminName(adminName);

	    if (existingAdmin != null && existingAdmin.getAdminPassword().equals(password)) {
	        // Successfully logged in, return a success message
	        return "Login successful!";
	    } else {
	        // Wrong credentials, throw an exception
	        throw new AdminNotFoundException("Wrong user name and password");
	    }
	}

}
