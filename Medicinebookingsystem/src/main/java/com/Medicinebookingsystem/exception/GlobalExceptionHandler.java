package com.Medicinebookingsystem.exception;

 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

 

 

 

 

@ControllerAdvice

public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	@ResponseStatus(HttpStatus.BAD_REQUEST)   
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {       
		return new ResponseEntity<>("Validation error: " + ex.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);     }

	@ExceptionHandler(InsufficientMedicineQuantityException.class)

    public ResponseEntity<String> handleInsufficientMedicineQuantityException(InsufficientMedicineQuantityException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

 

    @ExceptionHandler(MedicineNotFoundException.class)

    public ResponseEntity<String> handleMedicineNotFoundException(MedicineNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UserNotFoundException.class)

    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InsufficientWalletBalanceException.class)

    public ResponseEntity<String> handleInsufficientWalletBalanceException(InsufficientWalletBalanceException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(AdminNotFoundException.class)

    public ResponseEntity<String> handleAdminNotFoundException(AdminNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BookingNotFoundException.class)

    public ResponseEntity<String> handleBookingNotFoundException(BookingNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(EmailAlreadyExistsException.class)

    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

}

 