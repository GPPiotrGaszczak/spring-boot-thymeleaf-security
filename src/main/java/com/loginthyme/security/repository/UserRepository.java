package com.loginthyme.security.repository;

import com.loginthyme.security.model.MyUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<MyUser, ObjectId> {
    MyUser findByEmail(String email);
    boolean existsByEmail(String email);
    List<MyUser> findByRole(String role);
    MyUser findById(String id);
}