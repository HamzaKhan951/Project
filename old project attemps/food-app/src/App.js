import React from 'react';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className="Container" >
      <div className = "App">
        <form className = "form">
          <h1>Log In</h1>
          
          <input className="input" type="email" name="email" placeholder="email"/>
          
          <input className="input" type="password" name="password" placeholder="password"/>
          
          
          <button name="login" className="button">Sign Up</button>
        </form>
      </div>
      <div className = "App">
        <form className = "form">
          <h1>Sign up</h1>
          
          <input className="input" type="email" name="email" placeholder="email"/>

          <input className="input" type="text" name="email" placeholder="country"/>

          <input className="input" type="number" name="age" placeholder="age"/>
          
          <input className="input" type="password" name="password" placeholder="password"/>
          
          <input className="input" type="password" name="re_password" placeholder ="re-enter password"/>
          <button name="login" className="button" >Sign Up</button>
        </form>
      </div>
    </div>
  );
}

export default App;
