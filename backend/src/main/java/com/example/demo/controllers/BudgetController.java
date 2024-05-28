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


import com.example.demo.entities.Budget;
import com.example.demo.services.BudgetService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService)
    {
        this.budgetService = budgetService;
    }

    @PostMapping("/addBudget")
    public String addBudget(@RequestBody Budget budget)
    {
        Budget newBudget = budgetService.addBudget(budget);
        return budgetService.findByID(newBudget.getID()).getID().toHexString();
    }

    @GetMapping("/{id}")
    public Budget getBudgetByID(@PathVariable ObjectId id)
    {
        return budgetService.findByID(id);
    }

    @GetMapping("/getAllBudgets")
    public List<Budget> getAllBudgets(String userID)
    {
        ObjectId registeredUser = new ObjectId(userID);
        return budgetService.findAllBudgetsByUserID(registeredUser);
    }

    @PatchMapping("/updateBudget")
    public Budget updateBudget(@RequestBody Budget budget)
    {
        return budgetService.updateBudget(budget.getID(), budget);
    } 

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable ObjectId id)
    {
        budgetService.deleteBudget(id);
    }

    @GetMapping("/getAllBudgetOid")
    public List<String> getAllBudgetOid(String userID) {
        ObjectId registeredUser = new ObjectId(userID);
        List<Budget> allBudgets = budgetService.findAllBudgetsByUserID(registeredUser);

        ArrayList<String> oid = new ArrayList<String>();

        for (int i = 0; i < allBudgets.size(); i++) {
            oid.add(allBudgets.get(i).getID().toHexString());
        }

        return oid;
    }
}
