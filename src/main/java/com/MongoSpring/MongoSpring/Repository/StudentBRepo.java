package com.MongoSpring.MongoSpring.Repository;

import com.MongoSpring.MongoSpring.Model.StudentB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentBRepo extends MongoRepository<StudentB, Long> {
}
