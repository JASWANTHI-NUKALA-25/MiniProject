package com.Medicinebookingsystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.exception.AdminNotFoundException;

@Service
public interface AdminService {

	
	public Admin createAdmin(Admin admin);
	public String loginAdmin(String AdminName,String password) throws AdminNotFoundException;
	public Admin loginadmin(String adminEmail, String password);
}
