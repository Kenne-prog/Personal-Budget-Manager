import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';

const BudgetForm = ({ onSubmit }) => {
    const [budgetName, setBudgetName] = useState("");
    const [resetPeriodType, setResetPeriodType] = useState("");
    const [isFormValid, setIsFormValid] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ budgetName, resetPeriodType });
        setBudgetName("");
        setResetPeriodType("");
    };

    const handleClose = (close) => {
        close();
    };
    

    const checkFormValidity = () => {
        if (budgetName.trim() === "" || resetPeriodType.trim() === "") {
            setIsFormValid(false);
        } else {
            setIsFormValid(true);
        }
    };

    React.useEffect(() => {checkFormValidity();}, [budgetName, resetPeriodType]);

    return (
        <Popup trigger={<button className="add-button">Add Budget</button>} modal nested className="budget-popup">
            {close => (
                <div className="modal">
                    <button className="close" onClick={close}>&times;</button>
                    <div className="header"> Add Budget </div>
                    <div className="content">
                        <form onSubmit={handleSubmit}>
                            <label>
                                Budget Name:
                                <input type="text" value={budgetName} onChange={(e) => setBudgetName(e.target.value)} required/>
                            </label>
                            <label>
                                Reset Period:
                                <select value={resetPeriodType} onChange={(e) => setResetPeriodType(e.target.value)}>
                                    <option value="NONE">None</option>
                                    <option value="DAILY">Daily</option>
                                    <option value="WEEKLY">Weekly</option>
                                    <option value="MONTHLY">Monthly</option>
                                    <option value="QUARTERLY">Quarterly</option>
                                    <option value="BIANNUAL">Biannual</option>
                                    <option value="ANNUAL">Annual</option>
                                </select>
                            </label>
                            <button type="submit" disabled={!isFormValid} className="add-button" id="add-bud-button">Add Budget</button>
                        </form>
                    </div>
                </div>
            )}
        </Popup>
    );
};

export default BudgetForm;
