package org.example.learningmanagementsystem.Service;

import org.example.learningmanagementsystem.Model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CourseService {

    ArrayList<Course> courses = new ArrayList<>();

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public boolean updateCourse(String courseID, Course course){
        int courseIndex=courseIndex(courseID);

        if (courseIndex==courses.size()+1){
            return false;
        }

        courses.set(courseIndex,course);
        return true;
    }

    public boolean deleteCourse(String courseID){
        int courseIndex=courseIndex(courseID);

        if (courseIndex==courses.size()+1){
            return false;
        }

        courses.remove(courseIndex);
        return true;
    }

    public boolean assignTeacher(String courseID,String teacherName){
        int courseIndex=courseIndex(courseID);

        if (courseIndex==courses.size()+1){
            return false;//course not found
        }

        courses.get(courseIndex).setCourseTeacher(teacherName);
        return true;
    }

    public int changeDescription(String courseID,String description){
        if (description.length()<20){
            return 0;//description too short
        }

        int courseIndex = courseIndex(courseID);

        if (courseIndex==courses.size()+1){
            return 1;//course not found
        }

        courses.get(courseIndex).setCourseDescription(description);
        return 2;//completed
    }

    public Course searchByTitle(String title){
        for (Course course : courses) {
            if (course.getCourseTitle().equalsIgnoreCase(title)) {
                return course;
            }
        }
        return null;
    }

    public Course searchByID(String courseID){
        for (Course cours : courses) {
            if (cours.getCourseID().equals(courseID))
                return cours;
        }
        return null;
    }

    public int courseIndex(String courseID){
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseID().equals(courseID))
                return i;
        }

        return courses.size()+1;
    }
}
