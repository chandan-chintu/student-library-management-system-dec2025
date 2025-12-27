package com.example.student_library_management_system.service;

import com.example.student_library_management_system.enums.CardStatus;
import com.example.student_library_management_system.model.Card;
import com.example.student_library_management_system.model.Student;
import com.example.student_library_management_system.repository.StudentRepository;
import com.example.student_library_management_system.requestdto.StudentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public String saveStudent(StudentRequestDto studentRequestDto){
        // convert the request dto into model class

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setMobile(studentRequestDto.getMobile());
        student.setDob(studentRequestDto.getDob());
        student.setDept(studentRequestDto.getDept());
        student.setSem(studentRequestDto.getSem());
        student.setAddress(studentRequestDto.getAddress());
        student.setGender(studentRequestDto.getGender());

        // whenever student adds, card also gets added as part of cascading
        Card card = new Card();
        card.setCardStatus(CardStatus.ACTIVE);
        card.setExpiryDate(LocalDate.now().plusYears(3).toString());
        card.setStudent(student);
        student.setCard(card);

        studentRepository.save(student);
        return "Student saved successfully";
    }

    public Student findStudentById(int id){
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            throw new RuntimeException("Student not found with id : "+id);
        }
    }

    public List<Student> findAllStudents(){
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    /*
    Pagination - fetching or getting the records or data in the form of pages
    pagenumber - the number of page we want to see(0,1,2,3,4,5...)
    pagesize - total number of records in each page(fixed for each page)

    total number of record - 28, page size - 5
    0th page - 1-5
    1st page - 6-10
    2nd page - 11-15
    3rd page - 16-20
    4th page - 21-25
    5th page - 26-28

    total numbers of records-11, page size-3
    0th page - 1-3
    1st page - 4-6
    2nd page - 7-9
    3rd page - 10-11

    // only pagination
    public List<Student> findStudentByPage(int pageNo, int pageSize){
        List<Student> studentList = studentRepository.findAll(PageRequest.of(pageNo,pageSize)).getContent();
        return studentList;
    }
*/

    // pagination and sorting by name
    public List<Student> findStudentByPage(int pageNo, int pageSize){
        List<Student> studentList = studentRepository.findAll(PageRequest.of(pageNo,pageSize, Sort.by("name"))).getContent();
        return studentList;
    }

    public String deleteStudentById(int id){
        Student existingStudent = findStudentById(id);
        if(existingStudent!=null) {
            studentRepository.deleteById(id);
            return "Student with id : " + id + " got deleted successfully";
        } else {
            return "Student with id : " + id + "is not present";
        }
    }

    public String updateStudentUsingPut(int id, StudentRequestDto studentRequestDto){
        Student existingStudent = findStudentById(id);
        if(existingStudent!=null) {
            existingStudent.setName(studentRequestDto.getName());
            existingStudent.setEmail(studentRequestDto.getEmail());
            existingStudent.setGender(studentRequestDto.getGender());
            existingStudent.setMobile(studentRequestDto.getMobile());
            existingStudent.setSem(studentRequestDto.getSem());
            existingStudent.setDob(studentRequestDto.getDob());
            existingStudent.setDept(studentRequestDto.getDept());
            existingStudent.setAddress(studentRequestDto.getAddress());
            studentRepository.save(existingStudent);
            return "Student with id : " + id + " got updated successfully";
        } else {
            return "Student with id : " + id + "is not present, hence cannot update";
        }
    }

    public Student findStudentByEmail(String email){
        Student student = studentRepository.findByStudentEmail(email);
        if(student!=null) {
            return student;
        }else{
            return null;
        }
    }

    public List<Student> findStudentByDept(String dept){
        List<Student> studentList = studentRepository.findStudentByDept(dept);
        return studentList;
    }
}
