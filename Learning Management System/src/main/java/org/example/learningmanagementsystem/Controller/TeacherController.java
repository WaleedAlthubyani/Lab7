package org.example.learningmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.ApiResponse.ApiResponse;
import org.example.learningmanagementsystem.Model.Teacher;
import org.example.learningmanagementsystem.Service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/learning-management-system/Teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/get")
    public ResponseEntity getTeachers(){
        ArrayList<Teacher> teachers = teacherService.getTeachers();
        return ResponseEntity.status(200).body(teachers);
    }

    @PostMapping("/add")
    public ResponseEntity addTeacher(@RequestBody @Valid Teacher teacher, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        teacherService.addTeacher(teacher);
        return ResponseEntity.status(200).body(new ApiResponse("Teacher added successfully"));
    }

    @PutMapping("/update/{teacherID}")
    public ResponseEntity updateTeacher(@PathVariable String teacherID, @RequestBody @Valid Teacher teacher, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated= teacherService.updateTeacher(teacherID,teacher);

        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Teacher updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
    }

    @DeleteMapping("/delete/{teacherID}")
    public ResponseEntity deleteTeacher(@PathVariable String teacherID){
        boolean isDeleted= teacherService.deleteTeacher(teacherID);

        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Teacher deleted successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
    }

    @PutMapping("/assign-a-grade/{teacherID}/{courseID}/{studentID}/{grade}")
    public ResponseEntity assignAGrade(@PathVariable String teacherID, @PathVariable String courseID, @PathVariable String studentID,@PathVariable double grade){
        int result = teacherService.assignAGrade(teacherID,courseID,studentID,grade);

        switch (result){
            case 0:return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
            case 1:return ResponseEntity.status(400).body(new ApiResponse("Student is not in the teacher's list"));
            case 3:return ResponseEntity.status(400).body(new ApiResponse("This teacher doesn't teach "+courseID));
            case 4:return ResponseEntity.status(400).body(new ApiResponse("A grade can't be negative"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Grade assigned successfully"));
    }

    @GetMapping("/view-course-students/{teacherID}/{courseID}")
    public ResponseEntity viewCourseStudents(@PathVariable String teacherID,@PathVariable String courseID){
        ArrayList<String> students = teacherService.viewCourseStudents(teacherID,courseID);
        if (students==null)
            return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));

        return ResponseEntity.status(200).body(students);
    }

    @PostMapping("/email-a-student/{teacherID}/{studentID}/{message}")
    public ResponseEntity emailAStudent(@PathVariable String teacherID,@PathVariable String studentID,@PathVariable String message){
        int result = teacherService.emailAStudent(teacherID,studentID,message);

        switch (result){
            case 0:return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
            case 1:return ResponseEntity.status(400).body(new ApiResponse("Student is not on the teacher's list"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Email sent successfully"));
    }
}
