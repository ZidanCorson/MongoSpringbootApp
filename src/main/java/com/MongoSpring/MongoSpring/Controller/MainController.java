package com.MongoSpring.MongoSpring.Controller;

import com.MongoSpring.MongoSpring.Model.DatabaseSequence;
import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Model.StudentB;
import com.MongoSpring.MongoSpring.Repository.StudentBRepo;
import com.MongoSpring.MongoSpring.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
public class MainController {
    @Autowired
    StudentRepo studentRepo;

    @Autowired
    StudentBRepo studentBRepo;

    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepo.findById(id).orElse(null);
    }

    @GetMapping("/fetchStudents")
    public List<Student> fetchStudents(){
        return studentRepo.findAll();
    }

    @GetMapping("/getMax")
    public Integer getMax(){
        return studentRepo.max();
    }

    @GetMapping("/getMin")
    public Integer getMin(){
        return studentRepo.min();
    }

    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student){
        Student studentTemp = new Student();
        studentTemp.setRno(generateSequence(Student.SEQUENCE_NAME));
        studentTemp.setName(student.getName());
        studentTemp.setAddress(student.getAddress());
        studentTemp.setMark(student.getMark());
        studentRepo.save(studentTemp);
    }

    @PostMapping("/addStudentB")
    public void addStudentB(@RequestBody StudentB student){
        StudentB studentTemp = new StudentB();
        studentTemp.setRno(generateSequence(StudentB.SEQUENCE_NAME));
        studentTemp.setName(student.getName());
        studentTemp.setAddress(student.getAddress());
        studentBRepo.save(studentTemp);
    }

    @PostMapping("/addStudentList")
    public void addStudent(@RequestBody List<Student> students){
        studentRepo.saveAll(students);
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
    public void deleteStudent(@PathVariable Long id){
        studentRepo.deleteById(id);
    }
}
