package org.example.learningmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.ApiResponse.ApiResponse;
import org.example.learningmanagementsystem.Model.Student;
import org.example.learningmanagementsystem.Service.CourseService;
import org.example.learningmanagementsystem.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/learning-management-system/Student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity getStudents(){
        ArrayList<Student> students = studentService.getStudents();
        return ResponseEntity.status(200).body(students);
    }

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody @Valid Student student, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        studentService.addStudent(student);
        return ResponseEntity.status(200).body(new ApiResponse("Student added successfully"));
    }

    @PutMapping("/update/{studentID}")
    public ResponseEntity updateStudent(@PathVariable String studentID, @RequestBody @Valid Student student,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = studentService.updateStudent(studentID,student);

        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Student updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
    }

    @DeleteMapping("/delete/{studentID}")
    public ResponseEntity deleteStudent(@PathVariable String studentID){
        boolean isDeleted= studentService.deleteStudent(studentID);

        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Student deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
    }

    @PostMapping("signup-course/{studentID}/{courseID}")
    public ResponseEntity signupCourse(@PathVariable String studentID,@PathVariable String courseID){
        int result=studentService.signupCourse(studentID,courseService.searchByID(courseID));

        switch (result){
            case 0:return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
            case 1:return ResponseEntity.status(400).body(new ApiResponse("You are already registered in this course"));
            case 3:return ResponseEntity.status(400).body(new ApiResponse("Course not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("signup successful"));
    }

    @DeleteMapping("/withdraw-from-course/{studentID}/{courseID}")
    public ResponseEntity withdrawFromCourse(@PathVariable String studentID,@PathVariable String courseID){
        int isWithdrawn=studentService.withdrawFromCourse(studentID,courseID);

        switch (isWithdrawn){
            case 0:return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
            case 1:return ResponseEntity.status(400).body(new ApiResponse("Student is not registered in this course"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student withdrawn successfully"));
    }

    @GetMapping("/check-grades/{studentID}")
    public ResponseEntity checkGrades(@PathVariable String studentID){
        ArrayList<String>grades=studentService.checkGrades(studentID);
        if (grades==null)
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));

        return ResponseEntity.status(200).body(grades);
    }
}
