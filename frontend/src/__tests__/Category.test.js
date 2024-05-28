import React from 'react';
import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import Category from '../pages/BudgetExpenses.js';
import axios from 'axios';

jest.mock('axios');

describe('Category Component', () => {
    it('renders categories correctly', async () => {
        const categoryData = [
            { categoryID: '1', name: 'Category 1', description: 'Description 1', amountAllocated: 100 }
        ];

        axios.get.mockResolvedValueOnce({ data: categoryData });

        try {
            const response = await axios.get(`http://localhost:8080/categories/${data.categoryID}`);
        } catch (error) {
            console.error('Error loading categories:', error);
        }
    });
});

//test add
it('adds a new category', async () => {
    const categoryInfo = { 
        amount: 200,
        categoryName: 'New Category',
        description: 'New Category Description'
    };

    const newCategory = {
        categoryID: '3',
        name: categoryInfo.categoryName,
        description: categoryInfo.description,
        amountAllocated: categoryInfo.amount
    };

    axios.post.mockResolvedValueOnce({ data: '3' });
    axios.get.mockResolvedValueOnce({ data: newCategory });

    try {
        const response = await axios.post("http://localhost:8080/categories/addCategories", newCategory);
    } catch (error) {
        console.error('Error adding categories:', error);
    }
});

//test delete
it('deletes a category', async () => {
    const categoryData = [
        { categoryID: '1', name: 'Category 1', description: 'Description 1', amountAllocated: 100 },
        { categoryID: '2', name: 'Category 2', description: 'Description 2', amountAllocated: 200 }
    ];

    axios.get.mockResolvedValueOnce({ data: categoryData });

    render(<BrowserRouter><Category /></BrowserRouter>);

    axios.delete.mockResolvedValueOnce();

    try {
        const response = await axios.delete("http://localhost:8080/categories/deleteCategory", categoryData);
        setBudgetName(response.data.name);
    } catch (error) {
        console.error('Error adding categories:', error);
    }
});

//test adding expense
it('adds an expense to a category', async () => {

    const expenseInfo = {
        amount: 50,
        comment: 'Expense Comment'
    };

    const newExpense = {
        expenseID: '3',
        amount: expenseInfo.amount,
        comment: expenseInfo.comment
    };

    axios.post.mockResolvedValueOnce({ data: '3' });
    axios.get.mockResolvedValueOnce({ data: newExpense });

    try {
        const response = await axios.post("http://localhost:8080/expense/addExpense", newExpense);
    } catch (error) {
        console.error('Error adding expense:', error);
    }
});

//test delete expense
it('deletes an expense from a category', async () => {
    const expenseID = '3';

    axios.delete.mockResolvedValueOnce();

    try {
        const response = await axios.delete(`http://localhost:8080/expense/${expenseID}`);
    } catch (error) {
        console.error('Error deleting expense:', error);
    }
});
