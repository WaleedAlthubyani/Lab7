package org.example.learningmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Teacher {

    @NotEmpty(message = "Please provide the teacher's ID")
    @Pattern(regexp = "^[0-9]{10}$",message = "ID must be 10 digits long")
    private String teacherID;

    @NotEmpty(message = "Please provide the teacher's name")
    @Size(min = 2, message = "The teacher's name can't be shorter than 2 characters")
    private String teacherName;

    @NotEmpty(message = "Please provide the teacher's email")
    @Email(message = "Please enter a valid email address")
    private String teacherEmail;

    @NotEmpty(message = "Please provide the teacher with a strong password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must contain at least one digit [0-9].\nPassword must contain at least one lowercase Latin character [a-z].\nPassword must contain at least one uppercase Latin character [A-Z].\nPassword must contain at least one special character like ! @ # & ( ).\nPassword must contain a length of at least 8 characters and a maximum of 20 characters.")
    private String teacherPassword;

    @JsonIgnore
    private ArrayList<Course> teacherCourses;
    @JsonIgnore
    private ArrayList<Student> teacherStudents;

}
