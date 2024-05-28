package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Category;
import com.example.demo.services.CategoryService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;  

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return categoryService.findByID(newCategory.getID()).getID().toHexString();
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable ObjectId id) {
        categoryService.removeCategory(id);
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable ObjectId id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/getAllCategories")
    public List<Category> getAllCategories(String budgetID) {

        ObjectId userBudget = new ObjectId(budgetID);
        return categoryService.findAllCategoriesByBudgetID(userBudget);
    }

    @PatchMapping("/updateCategory")
    public Category updateCategory(@RequestBody Category category)
    {
        return categoryService.updateCategory(category.getID(), category);
    } 

    @GetMapping("/getAllCategoryOid")
    public List<String> getAllCategoryOid(String budgetID) {
        ObjectId userBudget = new ObjectId(budgetID);
        List<Category> allCategories = categoryService.findAllCategoriesByBudgetID(userBudget);

        ArrayList<String> oid = new ArrayList<String>();

        for (int i = 0; i < allCategories.size(); i++) {
            oid.add(allCategories.get(i).getID().toHexString());
        }

        return oid;
    }
}