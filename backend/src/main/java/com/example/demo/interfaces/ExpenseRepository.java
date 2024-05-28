package com.example.demo.interfaces;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entities.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, ObjectId>{
    Optional<Expense> findById(ObjectId id);
    @Query("{ 'category_id' : ?0 }")
    List<Expense> findByCategoryId(ObjectId categoryID);
    void deleteById(ObjectId id);
}
