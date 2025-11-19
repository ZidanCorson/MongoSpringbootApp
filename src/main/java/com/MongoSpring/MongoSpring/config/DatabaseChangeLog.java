package com.MongoSpring.MongoSpring.config;

import com.MongoSpring.MongoSpring.Model.DatabaseSequence;
import com.MongoSpring.MongoSpring.Model.Student;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "seedDatabase", order = "001", author = "Zidan")
public class DatabaseChangeLog {
    private final MongoTemplate mongoTemplate;

    public DatabaseChangeLog(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    @Execution
    public void seedDatabase(){
        Student student1 = new Student();
        student1.setRno(1L);
        student1.setName("John Doe");
        student1.setAddress("123 Main St");
        student1.setMark(85);
        mongoTemplate.save(student1);

        Student student2 = new Student();
        student2.setRno(2L);
        student2.setName("Jane Smith");
        student2.setAddress("456 Oak Ave");
        student2.setMark(92);
        mongoTemplate.save(student2);
    }

    @RollbackExecution
    public void rollback(){
        mongoTemplate.dropCollection(Student.class);
    }

}
