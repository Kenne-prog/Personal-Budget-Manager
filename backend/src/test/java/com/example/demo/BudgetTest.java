package com.example.demo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.demo.entities.Budget;

public class BudgetTest {
    private Budget budget = new Budget();

    @Test  
    public void idCheck()
    {
        assert budget.setID(null) == false: "Budget ID cannot be null";
        System.out.println("ID check passed!");
    }

    @Test
    public void resetTypeCheck()
    {
        assert budget.setResetPeriodType(null) == false: "Budget reset period type cannot be null";
        System.out.println("Reset period type check passed!");
    }

    @Test
    public void resetDeadlineCheck()
    {
        assert budget.setResetDeadline(null) == false: "Budget deadline cannot be null";
        assert budget.setResetDeadline(LocalDate.MIN) == false: "Budget deadline cannot be before current date";
        assert budget.setResetDeadline(LocalDate.MAX) == false: "Budget deadline cannot be more than a year away from current date";
        assert budget.setResetDeadline(LocalDate.now().plusYears(1)) == true : "Budget deadline should be allowed to be allowed to be up to a year away (inclusive)";
        System.out.println("Reset deadline check passed!");
    }

    @Test
    public void userIdCheck()
    {
        assert budget.setUserID(null) == false: "Budget user ID cannot be null";
        System.out.println("User ID check passed!");
    }

    @Test 
    public void nameCheck()
    {
        assert budget.setName(null) == false : "Budget name cannot be null";
        assert budget.setName("") == false : "Budget name cannot be empty";
        assert budget.setName("  ") == false: "Budget name cannot be blank";
        assert budget.setName("a".repeat(101))
                                        == false : "Budget name cannot exceed 100 characters";
        System.out.println("Name check passed!");
    }

}