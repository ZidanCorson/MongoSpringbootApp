package com.MongoSpring.MongoSpring.config;

import com.MongoSpring.MongoSpring.Model.Student;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@ChangeUnit(id = "addBonusMarks", order = "004", author = "Zidan")
public class DatabaseChangeLog004_AddBonusMarks {
    
    private final MongoTemplate mongoTemplate;
    
    public DatabaseChangeLog004_AddBonusMarks(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    @Execution
    public void addFiveMarksToAllStudents() {
        // Query all students
        Query query = new Query();
        
        // Increment mark by 5 using $inc operator
        Update update = new Update().inc("mark", 5);
        
        // Apply to all students
        mongoTemplate.updateMulti(query, update, Student.class);
        
        System.out.println("Added 5 bonus marks to all students!");
    }
    
    @RollbackExecution
    public void rollback() {
        // If rollback is needed, subtract 5 marks
        Query query = new Query();
        Update update = new Update().inc("mark", -5);
        mongoTemplate.updateMulti(query, update, Student.class);
        
        System.out.println("Rollback: Removed 5 bonus marks from all students");
    }
}
