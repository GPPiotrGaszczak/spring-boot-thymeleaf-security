package com.loginthyme.security.model;

import com.loginthyme.security.annotation.FieldsValueMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "The password fields must match")
public class MyUser {
    @Id
    private ObjectId id;
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Password can not be empty")
    private String password;
    @Transient
    private String confirmPassword;
    @Min(value = 18, message = "Age must be at least 18")
    @NotNull(message = "Age can not be empty")
    private Integer age;
    private Set<Role> role;
}