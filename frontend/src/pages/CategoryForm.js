import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';

const CategoryForm = ({ onSubmit }) => {
    const [categoryName, setCategoryName] = useState("");
    const [amount, setAmount] = useState("");
    const [description, setDescription] = useState("");
    const [isFormValid, setIsFormValid] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ categoryName, amount, description });
        setCategoryName("");
        setAmount("");
        setDescription("");
    };

    const handleClose = (close) => {
        close();
    };
    

    const checkFormValidity = () => {
        if (categoryName.trim() === "" || amount.trim() === "" || description.trim() === "") {
            setIsFormValid(false);
        } else {
            setIsFormValid(true);
        }
    };

    React.useEffect(() => {checkFormValidity();}, [categoryName, amount, description] );

    return (
        <Popup className="category-popup" trigger={<button className="add-button">Add Category</button>} modal nested>
            {close => (
                <div className="modal">
                    <button className="close" onClick={close}>&times;</button>
                    <div className="header"> Add Category </div>
                    <div className="content">
                        <form onSubmit={handleSubmit}>
                            <label className="input-category-c">
                                Category Name:
                                <input type="text" value={categoryName} onChange={(e) => setCategoryName(e.target.value)} required />
                            </label>
                            <label className="input-category-c">
                                Category Description:
                                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} required />
                            </label>
                            <label className="input-category-c">
                                Amount allocated to Category:
                                <input type="number" value={amount} onChange={(e) => setAmount(e.target.value)} required />
                            </label>   
                            <button type="submit" disabled={!isFormValid} className="add-button" id="add-cat-button">Add Category</button>
                        </form>
                    </div>
                </div>
            )}
        </Popup>
    );
};

export default CategoryForm;
