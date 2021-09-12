import { useHistory } from "react-router";
import { useState } from "react";

const SignUp = () => {
    const [name,SetName] = useState('');
    const [address,setAddress] = useState('');
    const [email,setEmail]=useState('');
    const [password,setPassword]=useState('');
    const history=useHistory(); 

    const handleSubmit=(e)=>{
        e.preventDefault();
        const details={name,email,password,address};
        console.log(details);
        fetch('http://localhost:8086/signUp',{
            method:'POST',
            headers:{"Content-Type":"application/json"},
            body :JSON.stringify(details)
        }).then(res=>{
            if(!res.ok){
              console.log('Not Able to Fetch Data');
              history.push("/");
            }
            sessionStorage.setItem("user",JSON.stringify(details));
            history.push("/Main");
        })   
    }

    return (  
        <div className="login">
            <form className="signform" onSubmit={handleSubmit}>
                Enter Name <input type="text" value={name}
                onChange={(e)=>SetName(e.target.value)}></input><br></br><br></br>
                Enter Address <input type="text" value={address}
                onChange={(e)=>setAddress(e.target.value)}></input><br></br><br></br>
                Enter Email <input type="email" value={email}
                onChange={(e)=>setEmail(e.target.value)}></input><br></br><br></br>
                Enter Password <input type="password" value={password}
                onChange={(e)=>setPassword(e.target.value)}></input><br></br><br></br>
                <button type="submit" className="submit"><b>Sign Up</b></button>
            </form>
        </div>
    );
}
 
export default SignUp;