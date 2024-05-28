package com.example.demo.interfaces;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entities.Budget;

public interface BudgetRepository extends MongoRepository<Budget, ObjectId>{
    Optional<Budget> findById(ObjectId id);
    @Query("{ 'user_id' : ?0 }")
    List<Budget> findByUserId(ObjectId user_id);
    void deleteById(ObjectId id);
}  
