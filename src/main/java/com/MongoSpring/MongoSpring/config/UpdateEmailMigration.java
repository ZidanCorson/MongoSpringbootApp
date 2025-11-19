package com.MongoSpring.MongoSpring.config;

import com.MongoSpring.MongoSpring.Model.Student;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@ChangeUnit(id="updateExistingEmails", order = "003", author = "Zidan")
public class UpdateEmailMigration {
    private final MongoTemplate mongoTemplate;

    public UpdateEmailMigration(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void updateEmptyEmails(){
        // Update only students with empty email
        Query query = query(where("email").is(""));
        Update update = new Update().set("email", "student@school.com");
        mongoTemplate.updateMulti(query, update, Student.class);
    }

    @RollbackExecution
    public void rollback(){
        Query query = query(where("email").is("student@school.com"));
        Update update = new Update().set("email", "");
        mongoTemplate.updateMulti(query, update, Student.class);
    }
}
