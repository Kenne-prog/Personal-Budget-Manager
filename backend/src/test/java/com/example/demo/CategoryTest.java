package com.example.demo;

import org.bson.types.Decimal128;
import org.junit.jupiter.api.Test;

import com.example.demo.entities.Category;

public class CategoryTest {
    private Category category = new Category();

    @Test  
    public void idCheck()
    {
        assert category.setID(null) == false: "Category ID cannot be null";
        System.out.println("ID check passed!");
    }

    @Test 
    public void amountAllocatedCheck()
    {
        assert category.setAmountAllocated(null) == false : "Category amount allocated cannot be null";
        assert category.setAmountAllocated(new Decimal128(-1)) == false : "Category amount allocated cannot be negative";
        assert category.setAmountAllocated(Decimal128.NEGATIVE_INFINITY) == false : "Category amount allocated cannot be negative or infinite";
        assert category.setAmountAllocated(Decimal128.POSITIVE_INFINITY) == false : "Category amount allocated cannot be infinite";
        assert category.setAmountAllocated(Decimal128.NaN) == false : "Category amount allocated must be a number";
        System.out.println("Amount allocated check passed!");
    }

    @Test 
    public void nameCheck()
    {
        assert category.setName(null) == false : "Category name cannot be null";
        assert category.setName("") == false : "Category name cannot be empty";
        assert category.setName("  ") == false: "Category name cannot be blank";
        assert category.setName("a".repeat(101))
                                        == false : "Category name cannot exceed 100 characters";
        System.out.println("Name check passed!");
    }

    @Test
    public void descriptionCheck()
    {
        assert category.setDescription(null) == false : "Category description cannot be null";
        assert category.setDescription("   ") == false : "Category description cannot be all blank";
        assert category.setDescription("a".repeat(501)) == false : "Category description cannot be more than 500 characters";
        assert category.setDescription("") == true : "Category description should be allowed to be empty";
        System.out.println("Description check passed!");
    }

    @Test  
    public void budgetIdCheck()
    {
        assert category.setBudgetID(null) == false: "Category budget ID cannot be null";
        System.out.println("Budget ID check passed!");
    }
}