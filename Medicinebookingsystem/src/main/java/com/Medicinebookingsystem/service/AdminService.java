package com.Medicinebookingsystem.service;

import org.springframework.stereotype.Service;

import com.Medicinebookingsystem.entity.Admin;
import com.Medicinebookingsystem.exception.AdminNotFoundException;

@Service
public interface AdminService {

	
	public Admin createAdmin(Admin admin);
	public String loginAdmin(String AdminName,String password) throws AdminNotFoundException;
}
