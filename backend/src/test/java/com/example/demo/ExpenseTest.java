package com.example.demo;

import java.time.LocalDate;

import org.bson.types.Decimal128;
import org.junit.jupiter.api.Test;

import com.example.demo.entities.Expense;

public class ExpenseTest {
    private Expense expense = new Expense();

    public void idCheck()
    {
        assert expense.setID(null) == false : "Expense ID cannot be null";
        System.out.println("ID check passed");
    }

    @Test
    public void amountCheck()
    {
        assert expense.setAmount(null) == false : "Expense amount cannot be null";
        assert expense.setAmount(new Decimal128(-1)) == false : "Expense amount cannot be negative";
        assert expense.setAmount(Decimal128.NEGATIVE_INFINITY) == false : "Expense amount cannot be negative or infinite";
        assert expense.setAmount(Decimal128.POSITIVE_INFINITY) == false : "Expense amount cannot be infinite";
        assert expense.setAmount(Decimal128.NaN) == false : "Expense amount must be a number";
        System.out.println("Amount check passed!");
    }

    @Test
    public void commentCheck()
    {
        assert expense.setComment(null) == false : "Expense description cannot be null";
        assert expense.setComment("   ") == false : "Expense description cannot be all blank";
        assert expense.setComment("a".repeat(501)) == false : "Expense description cannot be more than 500 characters";
        assert expense.setComment("") == true : "Expense description should be allowed to be empty";
        System.out.println("Comment check passed!");
    }

    @Test
    public void dateEnteredCheck()
    {
        assert expense.setDateEntered(null) == false: "Expense date entered cannot be null";
        assert expense.setDateEntered(LocalDate.now().minusDays(1)) == false: "Expense date entered cannot be before current date";
        assert expense.setDateEntered(LocalDate.now().plusDays(1)) == false: "Expense date entered cannot be after current date";
        assert expense.setDateEntered(LocalDate.now()) == true : "Expense date entered should be today only";
        System.out.println("Date entered check passed!");
    }

    @Test
    public void categoryIdCheck()
    {
        assert expense.setCategoryID(null) == false : "Expense category ID cannot be null";
        System.out.println("Category ID check passed");
    }
}
