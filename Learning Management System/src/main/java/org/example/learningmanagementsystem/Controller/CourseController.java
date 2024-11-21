package org.example.learningmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.ApiResponse.ApiResponse;
import org.example.learningmanagementsystem.Model.Course;
import org.example.learningmanagementsystem.Service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/learning-management-system/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity getCourses(){
        ArrayList<Course> courses=courseService.getCourses();
        return ResponseEntity.status(200).body(courses);
    }

    @PostMapping("/add")
    public ResponseEntity addCourse(@RequestBody @Valid Course course, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        courseService.addCourse(course);
        return ResponseEntity.status(200).body(new ApiResponse("Course added successfully"));
    }

    @PutMapping("/update/{courseID}")
    public ResponseEntity updateCourse(@PathVariable String courseID,@RequestBody @Valid Course course, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated=courseService.updateCourse(courseID,course);

        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Course updated successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Course not found"));
    }

    @DeleteMapping("/delete/{courseID}")
    public ResponseEntity deleteCourse(@PathVariable String courseID){
        boolean isDeleted = courseService.deleteCourse(courseID);

        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Course deleted successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Course not found"));
    }

    @PutMapping("/assign-teacher/{courseID}/{teacherName}")
    public ResponseEntity assignTeacher(@PathVariable String courseID,@PathVariable String teacherName){
        boolean isAssigned=courseService.assignTeacher(courseID,teacherName);
        if (isAssigned){
            return ResponseEntity.status(200).body(new ApiResponse("Teacher assigned successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Course not found"));
    }

    @PutMapping("/change-description/{courseID}/{message}")
    public ResponseEntity changeDescription(@PathVariable String courseID,@PathVariable String message){
        int result=courseService.changeDescription(courseID,message);

        switch (result){
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("Description is too short"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("Course not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Description changed successfully"));
    }

    @GetMapping("/search-by-title/{title}")
    public ResponseEntity searchByTitle(@PathVariable String title){
        Course course = courseService.searchByTitle(title);

        if (course==null)
            return ResponseEntity.status(400).body(new ApiResponse("Course not found"));

        return ResponseEntity.status(200).body(course);
    }
}
