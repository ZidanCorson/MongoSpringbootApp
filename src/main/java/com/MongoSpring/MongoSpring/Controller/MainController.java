package com.MongoSpring.MongoSpring.Controller;

import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentRepo.findById(id).orElse(null);
    }

    @GetMapping("/fetchStudents")
    public List<Student> fetchStudents(){
        return studentRepo.findAll();
    }

    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student){
        studentRepo.save(student);
    }

    @PutMapping("/updateStudent")
    public void updateStudent(@RequestBody Student student){
        Student data = studentRepo.findById(student.getRno()).orElse(null);
        if (data!= null){
            data.setName(student.getName());
            data.setAddress(student.getAddress());
            studentRepo.save(data);
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Integer id){
        studentRepo.deleteById(id);
    }



}
