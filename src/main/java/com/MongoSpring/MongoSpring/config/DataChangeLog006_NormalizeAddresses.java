package com.MongoSpring.MongoSpring.config;
import com.MongoSpring.MongoSpring.Model.Student;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ChangeUnit(id = "normalizeAddresses", order = "011", author = "Zidan")
public class DataChangeLog006_NormalizeAddresses {
    private final MongoTemplate mongoTemplate;

    public DataChangeLog006_NormalizeAddresses(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void normalize() {
        List<Student> students = mongoTemplate.findAll(Student.class);

        for (Student s : students) {
            if (s.getAddress() == null) continue;

            String normalized = toTitleCase(s.getAddress());
            Query q = Query.query(Criteria.where("rno").is(s.getRno()));
            Update u = new Update().set("address", normalized);
            mongoTemplate.updateFirst(q, u, Student.class);
        }
    }

    @RollbackExecution
    public void rollback() {
    }

    private String toTitleCase(String input) {
        return Arrays.stream(input.trim().toLowerCase().split("\\s+"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1))
                .collect(Collectors.joining(" "));
    }
}
