package com.MongoSpring.MongoSpring.config;

import com.MongoSpring.MongoSpring.Model.Student;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


@ChangeUnit(id="addEmailField", order = "002", author = "Zidan")
public class AddEmailMigration {
    private final MongoTemplate mongoTemplate;

    public AddEmailMigration(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    @Execution
    public void addEmailToStudent(){
        Query query = new Query();
        Update update = new Update().set("email", "");
        mongoTemplate.updateMulti(query, update, Student.class);
    }

    @RollbackExecution
    public void rollback(){
        Query query = new Query();
        Update update = new Update().unset("email");
        mongoTemplate.updateMulti(query, update, Student.class);
    }

}
