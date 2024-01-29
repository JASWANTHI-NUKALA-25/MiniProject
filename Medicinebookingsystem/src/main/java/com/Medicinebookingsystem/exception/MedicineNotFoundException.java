package com.Medicinebookingsystem.exception;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(String string) {
        super(string);
    }
}