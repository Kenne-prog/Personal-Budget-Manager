import React from 'react';
import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import BudgetManager from '../pages/BudgetManager.js';
import axios from 'axios';

jest.mock('axios');

describe('BudgetManager Component', () => {
    it('renders budgets correctly', async () => {
        const budgetData = [
            { budgetID: '1', name: 'Budget 1', reset_deadline: '2024-05-01', reset_period_type: 'DAILY' },
            { budgetID: '2', name: 'Budget 2', reset_deadline: '2024-05-02', reset_period_type: 'WEEKLY' }
        ];

        axios.get.mockResolvedValueOnce({ data: budgetData });

        try {
            const response = await axios.get(`http://localhost:8080/budgets/${budgetID}`);
        } catch (error) {
            console.error('Error loading budgets:', error);
        }
    });
});

//test add
it('adds a new budget', async () => {
    const budgetInfo = { 
        resetPeriodType: 'MONTHLY',
        budgetName: 'New Budget'
    };

    const newBudget = {
        budgetID: '3',
        name: budgetInfo.budgetName,
        reset_deadline: '2024-06-07',
        reset_period_type: 'MONTHLY'
    };

    axios.post.mockResolvedValueOnce({ data: '3' });
    axios.get.mockResolvedValueOnce({ data: newBudget });

    try {
        const response = await axios.post("http://localhost:8080/budgets/addBudgets", newBudget);
    } catch (error) {
        console.error('Error adding budgets:', error);
    }
});

//test delete
it('deletes a budget', async () => {
    const budgetsData = [
        { budgetID: '1', name: 'Budget 1', reset_deadline: '2024-06-07', reset_period_type: 'MONTHLY' },
        { budgetID: '2', name: 'Budget 2', reset_deadline: '2024-06-07', reset_period_type: 'MONTHLY' }
    ];

    axios.get.mockResolvedValueOnce({ data: budgetsData });

    render(<BrowserRouter><BudgetManager /></BrowserRouter>);

    axios.delete.mockResolvedValueOnce();

    try {
        const response = await axios.delete(`http://localhost:8080/budgets/${budgetID}`);
    } catch (error) {
        console.error('Error deleting budgets:', error);
    }
});
