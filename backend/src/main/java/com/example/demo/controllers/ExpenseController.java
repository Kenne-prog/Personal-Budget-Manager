package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Expense;
import com.example.demo.services.ExpenseService;

//MAY ADD getByDate(Date), getAmountsLessThan(Decimal), getAmountsGreaterThan(Decimal), etc. IF NEEDED

@CrossOrigin(origins = "http://localhost:3000")
@RestController 
@RequestMapping("/expenses")
public class ExpenseController {
    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService)
    {
        this.expenseService = expenseService;
    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestBody Expense expense)
    {
        Expense newExpense = expenseService.addExpense(expense);
        return expenseService.findByID(newExpense.getID()).getID().toHexString();
    }

    @GetMapping("/{id}")
    public Expense getExpenseByID(@PathVariable ObjectId id)
    {
        return expenseService.findByID(id);
    }

    @GetMapping("/getAllExpenses")
    public List<Expense> getAllExpenses(String categoryID)
    {
        ObjectId userCategories = new ObjectId(categoryID);
        return expenseService.findAllExpensesByCategoryID(userCategories);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable ObjectId id)
    {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/getAllExpenseOid")
    public List<String> getAllExpenseOid(String categoryID) {
        ObjectId userCategory = new ObjectId(categoryID);
        List<Expense> allExpenses = expenseService.findAllExpensesByCategoryID(userCategory);

        ArrayList<String> oid = new ArrayList<String>();

        for (int i = 0; i < allExpenses.size(); i++) {
            oid.add(allExpenses.get(i).getID().toHexString());
        }

        return oid;
    }

}
