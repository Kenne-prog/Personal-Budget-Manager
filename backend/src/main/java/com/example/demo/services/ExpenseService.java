package com.example.demo.services;

import java.util.List;

import org.bson.types.Decimal128;
import java.time.LocalDate;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Expense;
import com.example.demo.interfaces.ExpenseRepository;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository)
    {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense)
    {
        Expense newExpense = new Expense (expense.getAmount(),
                                          expense.getComment(),
                                          expense.getDateEntered(), 
                                          expense.getCategoryID());
        return expenseRepository.save(newExpense);
    }

    public Expense findByID(ObjectId id)
    {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpense(ObjectId id)
    {
        expenseRepository.deleteById(id);
    }

    public List<Expense> findAllExpensesByCategoryID(ObjectId categoryID)
    {
        return expenseRepository.findByCategoryId(categoryID);
    }
}
