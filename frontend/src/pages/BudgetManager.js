import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { FaTrash } from 'react-icons/fa';
import  CIcon  from '@coreui/icons-react';
import  * as icon  from '@coreui/icons';
import BudgetForm from './BudgetForm';
import CategoryForm from './CategoryForm';
import ExpenseForm from './ExpenseForm';
import Category from './BudgetExpenses';


const BudgetManager = () => {
    const [budgets, setBudgets] = useState([]);
    const [budgetID, setBudgetID] = useState("");
    const [allBudgetIDs, setAllBudgetIDs] = useState([]);
    const [deletedBudgetIDs, setDeletedBudgetIDs] = useState([]);
    const storedUserID = localStorage.getItem('userID');
    let date = new Date()

    //change deadline once deadline is met
    const getDeadline = (deadline, period) => {
        switch(period) {
            case "DAILY":
                deadline.setDate(deadline.getDate() + 1)
                break;
            case "WEEKLY":
                deadline.setDate(deadline.getDate() + 7)
                break;
            case "MONTHLY":
                deadline.setMonth(deadline.getMonth() + 1)
                break;
            case "QUARTERLY":
                deadline.setMonth(deadline.getMonth() + 3)
                break;
            case "BIANNUAL":
                deadline.setMonth(deadline.getMonth() + 6)
                break;
            case "ANNUAL":
                deadline.setMonth(deadline.getMonth() + 12)
                break;
            }
            const date = deadline.toISOString().split('T')[0]
            return date;
        }


    const deleteBudget = (budgetID) => {
        return axios.delete(`http://localhost:8080/budgets/${budgetID}`)
            .then(function(budgetResponse) {         
                
                setBudgets(budgets => budgets.filter(budgets => budgets.budgetID !== budgetID));
                axios.get(`http://localhost:8080/categories/getAllCategoryOid?budgetID=${budgetID}`)
                .then(response => {
                    //delete category and expenses based on budget
                    response.data.forEach((categoryID) => {
                        axios.get(`http://localhost:8080/expenses/getAllExpenseOid?categoryID=${categoryID}`)
                        .then(response => {
                            response.data.forEach((expenseID) =>
                                axios.delete(`http://localhost:8080/expenses/${expenseID}`)
                            )
                        })
                        axios.delete(`http://localhost:8080/categories/${categoryID}`)
                    })
                }); 
            })
            .catch(function(error) {
                console.error(error);
            });     
    };

    //load budgets
    useEffect(() => {
        let isMounted = true;
        axios.get(`http://localhost:8080/budgets/getAllBudgetOid?userID=${storedUserID}`)
        .then(function(response){
            if (isMounted) {
                
                const allBudgetIDs = response.data;
                setAllBudgetIDs(allBudgetIDs);
    
                allBudgetIDs.forEach(budgetID => {
                    axios.get(`http://localhost:8080/budgets/${budgetID}`)
                    .then(function(budgetResponse) {
                        
                        const budgetData = budgetResponse.data;
                        budgetData.budgetID = budgetID;
                        let deadline = new Date(budgetData.reset_deadline)
                        const currentDate = new Date()
                        
                        //if the reset deadline is met reset the categories within the budget and delete all expenses
                        if(deadline <= currentDate){
                            budgetData.reset_deadline = getDeadline(deadline, budgetData.resetPeriodType)
                            axios.get(`http://localhost:8080/categories/getAllCategoryOid?budgetID=${budgetID}`)
                            .then(response => {
                                response.data.forEach((categoryID) => {
                                    axios.get(`http://localhost:8080/expenses/getAllExpenseOid?categoryID=${categoryID}`)
                                    .then(response => {
                                        response.data.forEach((expenseID) =>
                                            axios.delete(`http://localhost:8080/expenses/${expenseID}`)
                                        );   
                                    })}
                                )}
                            )}

                        //refresh budgets
                        setBudgets(prevBudgets => {const updatedBudgets = [...prevBudgets, budgetResponse.data];
                        updatedBudgets.sort((a, b) => {
                                const dateA = new Date(a.id.date);
                                const dateB = new Date(b.id.date);
                                return dateA - dateB;
                        });
                            return updatedBudgets;
                        });
                    })
                    .catch(function(error) {
                        console.error(error);
                    });
                });
            }
        })
        .catch(function(error){
            alert(error);
        });
    
        return () => {
            isMounted = false; 
        };
    }, []);

    const addBudget = (budgetInfo) => {
      if (budgetInfo) {

        const budget = {
            reset_period_type: budgetInfo.resetPeriodType,
            user_id: storedUserID,
            name: budgetInfo.budgetName,
            reset_deadline: getDeadline(date, budgetInfo.resetPeriodType)
        }

        axios.post('http://localhost:8080/budgets/addBudget', budget)
        .then(function(response){
            axios.get(`http://localhost:8080/budgets/${response.data}`)
          .then(function(budgetResponse) {
            setBudgets(prevBudgets => {
                const updatedBudgets = [...prevBudgets, {...budgetResponse.data, budgetID: response.data}];
              
              updatedBudgets.sort((a, b) => {
                const dateA = new Date(a.id.date);
                const dateB = new Date(b.id.date);
                return dateA - dateB;
              });
              console.log("Budgets state after adding:", updatedBudgets);
              return updatedBudgets;
            });
          })
          .catch(function(error) {
            console.error(error);
          });
      })
      .catch(function(error){
        alert(error)
      })
    }
};

    return (
        <div className="budget-manager-container">
            <p style={{marginBottom: "1px", paddingLeft: "12px"}}><Link to={`/`}>Log Out</Link></p>
            <div>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                    <h1 className="cat-title">Budget Manager</h1>
                    <p className="cat-form"><BudgetForm onSubmit = {addBudget}/></p>
                </div>
                <table className="budgets-container">  
                    <thead  className="table-header"><tr><td>Action</td><td>Budgets</td><td>Budgets Next Reset Date</td><td>Budgets Period</td></tr><tr><td colSpan="4"><hr style={{width: "99%"}}/></td></tr></thead>
                    <tbody>
                        {budgets.map((budget, index) => (
                             <React.Fragment key={budget.budgetID}>
                             <tr>
                                 <td><button onClick={() => deleteBudget(budget.budgetID)} className="delete-budget"><FontAwesomeIcon icon={faTrash} /></button></td>
                                 <td><Link to={`/manage/${budget.budgetID}`}>{budget.name}</Link></td>
                                 <td>{budget.reset_deadline}</td>
                                 <td>{budget.reset_period_type}</td>
                             </tr>
                             <tr>
                                 <td colSpan="4"><hr style={{width: "99%"}}/></td>
                             </tr>
                         </React.Fragment>
                        ))}
                    </tbody>                    
                </table>
            </div>
        </div>
    );
}
export default BudgetManager;