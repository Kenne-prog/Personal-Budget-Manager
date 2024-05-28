package com.example.demo.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.interfaces.CategoryRepository; 

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByID(ObjectId id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category addCategory(Category category) {

        Category newCategory = new Category (category.getAmountAllocated(),
                                             category.getName(), 
                                             category.getDescription(), 
                                             category.getBudgetID());

        return categoryRepository.save(newCategory);
        
    }

    public void removeCategory(ObjectId id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(ObjectId id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> findAllCategoriesByBudgetID(ObjectId budgetID) {
        return categoryRepository.findByBudgetId(budgetID);
    }

    public Category updateCategory(ObjectId id, Category newCategory) {

        Category category = categoryRepository.findById(id).orElse(null);

        if(category != null) {
            return categoryRepository.save(newCategory);
        }
        return null;
        
    }

}
