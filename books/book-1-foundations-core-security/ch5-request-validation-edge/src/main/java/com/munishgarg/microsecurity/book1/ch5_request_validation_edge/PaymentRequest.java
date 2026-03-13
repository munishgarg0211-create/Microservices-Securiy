package com.munishgarg.microsecurity.book1.ch5_request_validation_edge;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PaymentRequest {

    // Must not be null or empty
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    // Must be a valid email format
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String userEmail;

    // Must be positive, up to a reasonable limit
    @Min(value = 1, message = "Amount must be at least 1")
    @Max(value = 10000, message = "Amount exceeds single transaction limit")
    private Integer amount;

    // Regex to strictly allow only alphanumeric and spaces, blocking script tags or SQL injection characters
    @NotBlank(message = "Description cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Description contains invalid characters. Only alphanumeric characters and spaces are allowed.")
    private String description;


    // --- Getters & Setters ---

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
