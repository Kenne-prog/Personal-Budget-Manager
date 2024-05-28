import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

const ExpenseForm = ({ onSubmit }) => {
    const [amount, setAmount] = useState("");
    const [comment, setComment] = useState("");
    const [isFormValid, setIsFormValid] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({amount, comment});
        setAmount("");
        setComment("");
    };

    const handleClose = (close) => {
        close();
    };
    

    const checkFormValidity = () => {
        if (amount.trim() === "" || comment.trim() === "") {
            setIsFormValid(false);
        } else {
            setIsFormValid(true);
        }
    };

    React.useEffect(() => {checkFormValidity();}, [amount, comment] );

    return (
        <Popup trigger={<button style={{backgroundColor: "transparent", border: "none"}}><FontAwesomeIcon icon={faPlus} /></button>} modal nested>
            {close => (
                <div className='modal'>
                    <button className='close' onClick={close}>&times;</button>
                    <div className='header'> Add Expense </div>
                    <div className='content'>
                        <form onSubmit={handleSubmit}>
                            <label>
                                Expense Comment:
                                <input type="text" value={comment} onChange={(e) => setComment(e.target.value)} required />
                            </label>
                            <label>
                                Amount of Expense:
                                <input type="number" value={amount} onChange={(e) => setAmount(e.target.value)} required />
                            </label>
                            <button type="submit" disabled={!isFormValid} className="add-button" id="add-exp-button">Add</button>
                        </form>
                    </div>
                </div>
            )}
        </Popup>
    );
};

export default ExpenseForm;
