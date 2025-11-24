package com.MongoSpring.MongoSpring.config;
import com.MongoSpring.MongoSpring.Model.Student;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;


@ChangeUnit(id = "addGradeField", order = "010", author = "Zidan")
public class DatabaseChangeLog005_AddGradeField {
    private final MongoTemplate mongoTemplate;

    public DatabaseChangeLog005_AddGradeField(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void addGrade() {
        List<Student> students = mongoTemplate.findAll(Student.class);

        for (Student s : students) {
            String grade;
            Integer mark = s.getMark();
            if (mark == null) {
                grade = "N/A";
            } else if (mark >= 85) {
                grade = "A";
            } else if (mark >= 70) {
                grade = "B";
            } else if (mark >= 50) {
                grade = "C";
            } else {
                grade = "D";
            }

            Query q = Query.query(Criteria.where("rno").is(s.getRno()));
            Update u = new Update().set("grade", grade);
            mongoTemplate.updateFirst(q, u, Student.class);
        }
    }

    @RollbackExecution
    public void rollback() {
        Query q = new Query();
        Update u = new Update().unset("grade");
        mongoTemplate.updateMulti(q, u, Student.class);
    }
}
