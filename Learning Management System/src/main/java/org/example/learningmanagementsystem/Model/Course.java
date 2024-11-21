package org.example.learningmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {

    @NotEmpty(message = "Please provide an ID for the course")
    @Pattern(regexp = "^[a-zA-Z]{1,3}[0-9]{3}$",message = "course id can only be in the pattern of CCC111 where C represent a letter and 1 represent a number.\nYou can use 1 to three letters but you must use 3 numbers")
    private String courseID;

    @NotEmpty(message = "Please provide a title for the course")
    @Size(min = 5,message = "Course title can't be less than 5 characters long")
    private String courseTitle;

    @NotEmpty(message = "Please provide a description for this course")
    @Size(min = 20,message = "Course description can't be less than 20 characters long")
    private String courseDescription;

    @JsonIgnore
    private String courseTeacher;

    @Positive(message = "The credits for this course must be a positive number")
    private int courseCredit;

}
