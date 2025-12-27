package com.example.student_library_management_system.controller;

import com.example.student_library_management_system.model.Student;
import com.example.student_library_management_system.requestdto.StudentRequestDto;
import com.example.student_library_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/apis")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/save")
    public String saveStudent(@RequestBody StudentRequestDto studentRequestDto){
        String response = studentService.saveStudent(studentRequestDto);
        return response;
    }

    @GetMapping("/findById/{id}")
    public Object findStudentById(@PathVariable int id){
        try {
            Student student = studentService.findStudentById(id);
            return student;
        } catch (Exception e){
            System.out.println("exception occurred : "+e.getMessage()+"---"+e.getClass());
            return "exception occurred : "+e.getMessage()+"---"+e.getClass();
        }
    }

    @GetMapping("/findAll")
    public List<Student> findAllStudent(){
        List<Student> student = studentService.findAllStudents();
        return student;
    }

    @GetMapping("/findByPage")
    public List<Student> findStudentByPage(@RequestParam int pageNo,@RequestParam int pageSize){
        List<Student> studentList = studentService.findStudentByPage(pageNo, pageSize);
        return studentList;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable int id){
       String response = studentService.deleteStudentById(id);
       return response;
    }

    @PutMapping("/update/{id}")
    public String updateStudentById(@PathVariable int id, @RequestBody StudentRequestDto studentRequestDto){
        String response = studentService.updateStudentUsingPut(id, studentRequestDto);
        return response;
    }

    @GetMapping("/findByEmail")
    public Student findStudentByEmail(@RequestParam String email){
        Student student = studentService.findStudentByEmail(email);
        return student;
    }

    @GetMapping("/findByDept")
    public List<Student> findStudentByDept(@RequestParam String dept){
        List<Student> student = studentService.findStudentByDept(dept);
        return student;
    }

}
