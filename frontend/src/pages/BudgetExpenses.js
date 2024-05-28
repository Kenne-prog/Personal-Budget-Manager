import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import CategoryForm from './CategoryForm';
import ExpenseForm from './ExpenseForm';

const Category = () => {
    //state variables
    const {budgetID} = useParams()
    const [categories, setCategories] = useState([]);
    const [categoryID, setCategoryID] = useState("");
    const [allCategoryIDs, setAllCategoryIDs] = useState([]);
    const [budget, setBudget] = useState([]);
    const [budgetName, setBudgetName] = useState("")
    const [deadline, setDeadline] = useState("")
    
    //fetch all categories
    useEffect(() => {
        let isMounted = true;
        axios.get(`http://localhost:8080/budgets/${budgetID}`)
        .then(response => setBudget(response.data));
        
        //get budget names
        axios.get(`http://localhost:8080/budgets/${budgetID}`)
        .then(function(response){
            setBudgetName(response.data.name);
            setDeadline(response.data.reset_deadline)
        });

        axios.get(`http://localhost:8080/categories/getAllCategoryOid?budgetID=${budgetID}`)
        .then(function(response){
            if (isMounted) {
                
                const allCategoryIDs = response.data;
                setAllCategoryIDs(allCategoryIDs);
                
                //go through all category ids that have the same budget id
                if (allCategoryIDs.length > 0) {
                    allCategoryIDs.forEach(categoryID => {
                        axios.get(`http://localhost:8080/categories/${categoryID}`)
                        .then(function(categoryResponse) {

                            const categoryData = categoryResponse.data;
                            categoryData.categoryID = categoryID;

                            setCategories(categories => {const updatedCategories = [...categories, categoryResponse.data];
                                //sort based on when added
                                updatedCategories.sort((a, b) => {
                                    const dateA = new Date(a.id.date);
                                    const dateB = new Date(b.id.date);
                                    return dateA - dateB;
                            });
                            return updatedCategories;
                        });
                    })
                    .catch(function(error) {
                        console.error(error);
                    });
                })};
            }
        })
        .catch(function(error){
            console.error(error);
        });
    
        return () => {
            isMounted = false; 

        };
    }, []);


    const addCategory = (categoryInfo) => {
        if (categoryInfo) {

            const category = {
                amount_allocated: categoryInfo.amount,
                name: categoryInfo.categoryName,
                description: categoryInfo.description,
                budget_id: budgetID
            }
            
            axios.post('http://localhost:8080/categories/addCategory', category)
            .then(function(response){
                axios.get(`http://localhost:8080/categories/${response.data}`)
                .then(function(categoryResponse) {
                    
                    setCategories(prevCategories => {
                      const updatedCategories = [...prevCategories, {...categoryResponse.data, categoryID: response.data}];
                      updatedCategories.sort((a, b) => {
                        const dateA = new Date(a.id.date);
                        const dateB = new Date(b.id.date);
                        return dateA - dateB;
                      });
                      return updatedCategories;
                    });

                })
                .catch(function(error) {
                    console.error(error);
                });
            })
            .catch(function(error){
                console.error(error);
            })
        }
    };
    

    return (
        <div className="budget-container">
            {budget !== "" && 
            <div>

                <p style={{marginBottom: "1px", paddingLeft: "12px"}}><Link to={`/manage`}>&lt;Budgets</Link></p>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                    <h1 className="cat-title">{budgetName}</h1>
                    <p className="cat-form"><CategoryForm onSubmit={addCategory} /></p>
                </div>

                <table className="expense-table">
                    <div className="categories-container">
                        <thead className="table-header"><tr><th className="table-cell" id="action-cell">Action</th><th className="table-cell">Category</th><th className="table-cell">Category Description</th><th className="table-cell" >Amount Allocated</th><th className="table-cell" id="expense-header">Expenses</th><th className="table-cell">Amount Left</th></tr></thead>
                        <td colSpan="6"><hr style={{width: "99%"}}/></td>
                        <tbody>
                            {categories.map((category, index) => (
                                <React.Fragment key={category.categoryID}>  
                                    <Expense index={index} key={category.categoryID} category={category} categories={categories} setCategories={setCategories} categoryID={category.categoryID} categoryAmount={category.amountAllocated} deadline={deadline}/>       
                                    <tr><td colSpan="6"><hr style={{width: "99%"}}/></td></tr>
                                </React.Fragment>         
                            ))}
                        </tbody>
                    </div>
                </table>
            </div>
            }
        </div>
    );
};

const Expense = ({ category, index, categories, setCategories, categoryID, categoryAmount, deadline }) => {
    const [expenses, setExpenses] = useState([]);
    let [remainingAmount, setRemainingAmount] = useState(categoryAmount);
    const [allExpensesIDs, setAllExpensesIDs] = useState([]);
    let currentDate = new Date()
    const date = currentDate.toISOString().split('T')[0]

    const deleteExpense = (expenseID) => {
        return axios.delete(`http://localhost:8080/expenses/${expenseID}`)
            .then(function(expenseResponse) {
                
                const deletedExpense = expenses.find(expense => expense.expenseID === expenseID);
                const deletedAmount = parseFloat(deletedExpense.amount);
                setRemainingAmount(remainingAmount+deletedAmount)
                setExpenses(expenses => expenses.filter(expense => expense.expenseID !== expenseID));
                
            })
            .catch(function(error) {
                console.error(error);
            });     
    };

    const deleteCategory = (categoryID) => {
        return axios.delete(`http://localhost:8080/categories/${categoryID}`)
            .then(function(expenseResponse) {
                //updates the categories for deleted category and expense
                setCategories(categories => categories.filter(categories => categories.categoryID !== categoryID));
                
                //gets expenses to delete with category
                axios.get(`http://localhost:8080/expenses/getAllExpenseOid?categoryID=${categoryID}`)
                .then(response => {
                    response.data.forEach((expenseID) => {
                        axios.delete(`http://localhost:8080/expenses/${expenseID}`)
                    })
                });

            })
            .catch(function(error) {
                console.error(error);
            });     
    };

    //load expenses
    useEffect(() => {
        let isMounted = true;
    
        axios.get(`http://localhost:8080/expenses/getAllExpenseOid?categoryID=${categoryID}`)
        .then(function(response){
            if (isMounted) {

                let totalExpenseAmount = 0;
                const allExpensesIDs = response.data;
                setAllExpensesIDs(allExpensesIDs);
                
                if (allExpensesIDs.length > 0) {
                    allExpensesIDs.forEach(expenseID => {
                    axios.get(`http://localhost:8080/expenses/${expenseID}`)
                    .then(function(expenseResponse) {

                        const expenseData = expenseResponse.data;
                        const expenseAmount = parseFloat(expenseData.amount);
                        
                        totalExpenseAmount += expenseAmount;


                        expenseData.expenseID = expenseID;

                        setExpenses(prevExpenses => {const updatedExpenses = [...prevExpenses, expenseResponse.data];
                            updatedExpenses.sort((a, b) => {
                                const dateA = new Date(a.id.date);
                                const dateB = new Date(b.id.date);
                                return dateA - dateB;
                        });
                            return updatedExpenses;
                        });

                        if (totalExpenseAmount > 0) {
                            const newRemainingAmount = categoryAmount - totalExpenseAmount;
                            setRemainingAmount(newRemainingAmount);
                        }

                    })
                    .catch(function(error) {
                        console.error(error);
                    });

                })};
            }
        })
        .catch(function(error){
        });
    
        return () => {
            isMounted = false; 
        };
    }, []);


    const addExpense = (expenseInfo) => {
        if (expenseInfo) {
            
            //calculate amount and notify
            const expenseAmount = parseFloat(expenseInfo.amount);
            const newRemainingAmount = remainingAmount - expenseAmount;
            const percentageSpent = ((categoryAmount - newRemainingAmount) / categoryAmount) * 100;
            const warningThreshold = 0;

            const currentDate = new Date()
            
            const reset = new Date(deadline)
            let timeDiff = reset - currentDate
            let dayDiff = Math.round(timeDiff / (1000 * 3600 * 24));

            if (percentageSpent >= warningThreshold) {
                alert(`You have spent ${percentageSpent.toFixed(2)}% of your budget. You have $${newRemainingAmount.toFixed(2)} left and ${dayDiff} days remaining in your reset period.`);
            }

            setRemainingAmount(newRemainingAmount);
            const expense = {
                amount: expenseInfo.amount,
                comment: expenseInfo.comment,
                date_entered: date,
                category_id: categoryID
            }
            
            axios.post('http://localhost:8080/expenses/addExpense', expense)
            .then(function(response){
                axios.get(`http://localhost:8080/expenses/${response.data}`)
                .then(function(expenseResponse) {

                    setExpenses(prevExpenses => {
                      const updatedExpenses = [...prevExpenses, {...expenseResponse.data, expenseID: response.data}];
                      updatedExpenses.sort((a, b) => {
                        const dateA = new Date(a.id.date);
                        const dateB = new Date(b.id.date);
                        return dateA - dateB;
                      });
                      return updatedExpenses;
                    });

                  })
                  .catch(function(error) {
                    console.error(error);
                  });

              })
              .catch(function(error){
                console.error(error);
              })

            }
        };

    return (
        <tr>
        <td className="table-cell"><button style={{backgroundColor: "transparent", border: "none"}} onClick = {() => deleteCategory(categoryID)}><FontAwesomeIcon icon={faTrash} /></button></td>
        <td className="table-cell">{category.name}</td>
        <td className="table-cell">{category.description}</td>
        <td className="table-cell">${categoryAmount}</td>
        <td className="table-cell" id="expense-cell" > 
        <ExpenseForm onSubmit = {addExpense} className="add-expense"/>
        <div className="list-container">
                {expenses.map((expense) => (
                    <React.Fragment key={expense.expenseID}>
                    <ul className="expense-container" key={expense.expenseID}>
                        <li><span style={{display:"table-cell"}}><button className="add-expense-button" style={{backgroundColor: "transparent", border: "none"}} onClick = {() => deleteExpense(expense.expenseID)}><FontAwesomeIcon icon={faTrash} /></button></span><span style={{display:"table-cell", wordBreak:"break-all", textWrap:"pretty"}}>{expense.comment} - ${expense.amount}</span></li>
                    </ul>
                    </React.Fragment>
                ))}
        </div>
        </td> 
        <td className="table-cell">${remainingAmount}</td>           
        </tr>
    );
};
export default Category;