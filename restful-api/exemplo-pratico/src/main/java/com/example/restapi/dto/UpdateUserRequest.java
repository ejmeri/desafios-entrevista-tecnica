package com.example.restapi.dto;

import com.example.restapi.model.User.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @Email(message = "Email deve ter formato válido")
    private String email;

    @Size(min = 6, max = 50, message = "Senha deve ter entre 6 e 50 caracteres")
    private String password;

    private UserStatus status;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String name, String email, String password, UserStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public UserStatus getStatus() {
        return this.status;
    }

    // Setters for Jackson
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    // Utility methods for checking if fields were provided
    public boolean hasName() {
        return name != null && !name.trim().isEmpty();
    }

    public boolean hasEmail() {
        return email != null && !email.trim().isEmpty();
    }

    public boolean hasPassword() {
        return password != null && !password.trim().isEmpty();
    }

    public boolean hasStatus() {
        return status != null;
    }

    // Builder pattern
    public static UpdateUserRequestBuilder builder() {
        return new UpdateUserRequestBuilder();
    }

    public static class UpdateUserRequestBuilder {
        private String name;
        private String email;
        private String password;
        private UserStatus status;

        public UpdateUserRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UpdateUserRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UpdateUserRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UpdateUserRequestBuilder status(UserStatus status) {
            this.status = status;
            return this;
        }

        public UpdateUserRequest build() {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setName(this.name);
            request.setEmail(this.email);
            request.setPassword(this.password);
            request.setStatus(this.status);
            return request;
        }
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + (password != null ? "[PROTECTED]" : null) + '\'' +
                ", status=" + status +
                '}';
    }
}
