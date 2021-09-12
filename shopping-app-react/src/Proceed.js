import useFetch from "./useFetch";
import {Link} from 'react-router-dom';
import { useHistory } from "react-router";

const Proceed = () => {
    const itemdetails = sessionStorage.getItem("items");
    const items = JSON.parse(itemdetails);
    const {data : fetchItems} = useFetch('http://localhost:8086/fetchItems');
    console.log(fetchItems);
    const history = useHistory();
    const handleSubmit=(e)=>{
        e.preventDefault();
        var purchaseList = JSON.parse(sessionStorage.getItem("items"));
        console.log(purchaseList);
        sessionStorage.setItem("items",JSON.stringify(purchaseList));
        fetch('http://localhost:8086/fetchTotal',{
            method:'POST',
            headers:{"Content-Type":"application/json"},
            body :JSON.stringify(purchaseList)
        }).then((res) =>{
            return res.json();
        }).then((data) =>{
            console.log("total : " + data);
            sessionStorage.setItem("total",data);
        });
        history.push("/Finalized"); 
    }
    return ( 
        <div className="proceed">
            <h2>Purchase Summary : </h2>
            <table border="1" className="table">
                <tr>
                    <th>Product ID</th>
                    <th>Quantity</th>
                </tr>
                <tr>
                    {items && items.map((item) => (
                        <div className="itemlist">
                            <td>{item.productId}</td>
                            <td>{item.quantity}</td>
                        </div>
                    ))}
                </tr>
            </table><br></br>
            <nav className="navbar">
                <div className="navbarProceed">
                    <Link to="/Main" style={{color: 'white'}}>Purchase more</Link>
                    <button className="submit" onClick={handleSubmit}> Confirm Purchase</button>
                </div>
            </nav>
        </div>
     );
}
 
export default Proceed;