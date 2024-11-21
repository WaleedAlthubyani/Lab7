package org.example.learningmanagementsystem.Service;

import org.example.learningmanagementsystem.Model.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeacherService {

    ArrayList<Teacher> teachers = new ArrayList<>();

    public ArrayList<Teacher> getTeachers(){
        return teachers;
    }

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }

    public boolean updateTeacher(String teacherID, Teacher teacher){
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getTeacherID().equals(teacherID)){
                teachers.set(i,teacher);
                return true;
            }
        }
        return false;
    }

    public boolean deleteTeacher(String teacherID){
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getTeacherID().equals(teacherID)){
                teachers.remove(i);
                return true;
            }
        }
        return false;
    }

    public int assignAGrade(String teacherID,String courseID,String studentID,double grade){

        if (grade<0)
            return 4;

        int studentTeacher=teacherIndex(teacherID);

        if (studentTeacher==teachers.size()+1)
            return 0;//teacher not found

        int student= studentIndex(teachers.get(studentTeacher), studentID);

        if (student==teachers.get(studentTeacher).getTeacherStudents().size()+1)
            return 1;//student not in teacher's list

        boolean teachCourse=false;
        for (int i = 0; i < teachers.get(studentTeacher).getTeacherCourses().size(); i++) {
            if (teachers.get(studentTeacher).getTeacherCourses().get(i).getCourseID().equalsIgnoreCase(courseID)){
                teachCourse=true;
                break;
            }
        }

        if (!teachCourse)
            return 3;

        for (int i = 0; i < teachers.get(studentTeacher).getTeacherStudents().get(student).getCourses().size(); i++) {
            if (teachers.get(studentTeacher).getTeacherStudents().get(student).getCourses().get(i).getCourseID().equalsIgnoreCase(courseID)){
                teachers.get(studentTeacher).getTeacherStudents().get(student).getGrades().set(i,grade);
                break;
            }
        }

        return 2;//approveAssigning
    }

    public ArrayList<String> viewCourseStudents(String teacherID,String courseID){
        ArrayList<String> courseStudents=new ArrayList<>();
        int teacher=teacherIndex(teacherID);

        if (teacher==teachers.size()+1)
            return null;

        for (int i = 0; i < teachers.get(teacher).getTeacherStudents().size(); i++) {
            for (int j = 0; j < teachers.get(teacher).getTeacherStudents().get(i).getCourses().size(); j++) {
                //if(teacher array->students array->Courses array.courseID == parameter courseID)
                if (teachers.get(teacher).getTeacherStudents().get(i).getCourses().get(j).getCourseID().equalsIgnoreCase(courseID)){
                    courseStudents.add(teachers.get(teacher).getTeacherStudents().get(i).getStudentName());
                    break;
                }
            }
        }
        return courseStudents;
    }

    public int emailAStudent(String teacherID,String studentID,String message){
        int teacher=teacherIndex(teacherID);
        if (teacher==teachers.size()+1){
            return 0;//teacher not found
        }

        int student=studentIndex(teachers.get(teacher),studentID);

        if (student==teachers.get(teacher).getTeacherStudents().size()+1){
            return 1;//student not found
        }

        //to be updated once I learn how to send actual emails
        String email=teachers.get(teacher).getTeacherStudents().get(student).getStudentEmail() + " "+message;
        return 2;
    }

    public int teacherIndex(String teacherID){
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getTeacherID().equals(teacherID)){
                return i;
            }
        }
        return teachers.size()+1;
    }

    public int studentIndex(Teacher teacher,String studentID){
        for (int i = 0; i < teacher.getTeacherStudents().size(); i++) {
            if (teacher.getTeacherStudents().get(i).getStudentID().equals(studentID)){
                return i;
            }
        }

        return teacher.getTeacherStudents().size()+1;
    }
}
