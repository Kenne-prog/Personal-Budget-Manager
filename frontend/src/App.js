import React from 'react';
import "./App.css";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './pages/Home';
import BudgetManager from './pages/BudgetManager';
import SignUp from './pages/SignUp';
import Category from './pages/BudgetExpenses';


function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route index element={<Home />} />
          <Route path="manage" element={<BudgetManager />} />
          <Route path="/manage/:budgetID" element={<Category/>} />
          <Route path="signup" element={<SignUp/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
