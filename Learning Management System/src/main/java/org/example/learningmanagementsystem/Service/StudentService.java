package org.example.learningmanagementsystem.Service;


import org.example.learningmanagementsystem.Model.Course;
import org.example.learningmanagementsystem.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {

    ArrayList<Student> students = new ArrayList<>();

    public ArrayList<Student> getStudents(){
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public boolean updateStudent(String studentID,Student student){
        int studentIndex=studentIndex(studentID);

        if (studentIndex==students.size()+1){
            return false;
        }

        students.set(studentIndex,student);
        return true;
    }

    public boolean deleteStudent(String studentID){
        int studentIndex = studentIndex(studentID);
        if (studentIndex==students.size()+1){
            return false;
        }
        students.remove(studentIndex);
        return true;
    }

    public int signupCourse(String studentID, Course course){
        int studentIndex = studentIndex(studentID);

        if (course==null)
            return 3;//course not found

        if (studentIndex==students.size()+1){
            return 0;//student not found
        }


        if(students.get(studentIndex).getCourses().contains(course))
            return 1;//course already registered

        students.get(studentIndex).getCourses().add(course);
        students.get(studentIndex).getGrades().add(0.0);
        return 2;//completed
    }

    public int withdrawFromCourse(String studentID,String courseID){
        int studentIndex = studentIndex(studentID);
        if (studentIndex==students.size()+1){
            return 0;//student not found
        }

        for (int i = 0; i < students.get(studentIndex).getCourses().size(); i++) {
            if (students.get(studentIndex).getCourses().get(i).getCourseID().equals(courseID)){
                students.get(studentIndex).getCourses().remove(i);
                students.get(studentIndex).getGrades().remove(i);
                return 2;//completed
            }
        }

        return 1;//course not found
    }

    public ArrayList<String> checkGrades(String studentID){
        int studentIndex = studentIndex(studentID);

        if (studentIndex==students.size()+1){
            return null;//student not found
        }

        ArrayList<String> grades=new ArrayList<>();

        for (int i = 0; i < students.get(studentIndex).getCourses().size(); i++) {
            grades.add(students.get(studentIndex).getCourses().get(i).getCourseTitle()+" "+students.get(studentIndex).getGrades().get(i));
        }

        return grades;
    }

    public int studentIndex(String studentID){
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID().equals(studentID)){
                return i;
            }
        }
        return students.size()+1;
    }
}
