package com.example.demo.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Budget;
import com.example.demo.interfaces.BudgetRepository;

@Service
public class BudgetService {
    private BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository)
    {
        this.budgetRepository = budgetRepository;
    }

    //Do budgets need names? If so, do we need to check if a named budget already exists?
    public Budget addBudget(Budget budget)
    {   
        Budget newBudget = new Budget (budget.getName(),
                                budget.getResetPeriodType(),
                                budget.getResetDeadline(), 
                                budget.getUserID());
        return budgetRepository.save(newBudget);
    }

    public Budget findByID(ObjectId id)
    {
        return budgetRepository.findById(id).orElse(null);
    }

    public Budget updateBudget(ObjectId id, Budget newBudget)
    {
        Budget budget = budgetRepository.findById(id).orElse(null);
        if(budget != null)
        {
            return budgetRepository.save(newBudget);
        }
        return null;
    }

    public void deleteBudget(ObjectId id)
    {
        budgetRepository.deleteById(id);
    }

    public List<Budget> findAllBudgetsByUserID(ObjectId userID) {
        return budgetRepository.findByUserId(userID);
    }
}
