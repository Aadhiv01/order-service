import {useState} from 'react';
import { useHistory } from 'react-router';
const Login = () => {
    const [email,setEmail]=useState('');
    const [password,setPassword]=useState('');
    const history=useHistory(); 

    const handleSubmit=(e)=>{
        e.preventDefault();
        const details={email,password};
        console.log(details);
        fetch('http://localhost:8086/login',{
            method:'POST',
            headers:{"Content-Type":"application/json"},
            body :JSON.stringify(details)
        }).then(res=>{
            if(!res.ok){
              console.log('Not Able to Fetch Data');
              history.push("/");
            }
            return res.json();
          })
          .then(data=>{
            console.log("Fetched");
            console.log(data);
            console.log(typeof(data));
            sessionStorage.setItem("user",JSON.stringify(data));
            history.push("/Main");
        }
        )   
    }
    return ( 
        <div className="login">
            <form className="form" onSubmit={handleSubmit}>
                Enter Email <input type="email" value={email}
                onChange={(e)=>setEmail(e.target.value)}></input><br></br><br></br>
                Enter Password <input type="password" value={password}
                onChange={(e)=>setPassword(e.target.value)}></input><br></br><br></br>
                <button type="submit" className="submit"><b>Login</b></button>
            </form>
        </div>
     );
}
 
export default Login;