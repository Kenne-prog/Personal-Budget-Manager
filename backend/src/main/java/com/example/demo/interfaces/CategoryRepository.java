package com.example.demo.interfaces;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entities.Category;

public interface CategoryRepository extends MongoRepository<Category, ObjectId>{
    Optional<Category> findById(ObjectId id);
    @Query("{ 'budget_id' : ?0 }")
    List<Category> findByBudgetId(ObjectId budgetID);
    void deleteById(ObjectId id);
}