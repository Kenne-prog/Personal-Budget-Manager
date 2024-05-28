import {React, useState} from 'react'
import { useNavigate, Link } from 'react-router-dom'
import axios from 'axios'

const Home = () => {
    const[formData, setFromData] = useState({
        email: "",
        password: ""
    });

    const handleChange = (e) => {
        setFromData({...formData, [e.target.name]: e.target.value})
    }

    const navigate = useNavigate(); 

    const handleLogin = (event) =>{
        event.preventDefault();

        const loginInfo = {
            email : formData.email,
            password : formData.password
        }

        axios.post('http://localhost:8080/users/login', loginInfo)
        .then(function(response){
            localStorage.setItem('userID', response.data.user_id);
            navigate('/manage');
        }).catch(function(error){
            alert("Sorry Email and Password Not Found")
        })
    }

    return(
        <div className="home-container">
            <div className="title-container">
                <h1>Welcome to Pennywise</h1>  
            </div>
            <form className="credential-form">
                <div className="credential-container">
                    <label className="credential-label">
                        <input
                            aria-label="E-Mail"
                            aria-required="true"
                            type="text"
                            className="credential-input"
                            name="email"
                            id="email"
                            placeholder="E-Mail"
                            value={formData.email}
                            onChange = {handleChange}
                            required
                        />
                    </label>
                    <label className="credential-label">
                        <input
                            type="password"
                            className="credential-input"
                            name="password"
                            id="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange = {handleChange}
                            required
                        />
                    </label>
                    <button type="submit" className="submit-button" onClick={handleLogin}>Log in</button>
                    <p>
                        Don't have an account? 
                        <Link className="account-link" to="/signup">Sign Up</Link>
                    </p>
                </div>
            </form>
        </div>
    )
}

export default Home;