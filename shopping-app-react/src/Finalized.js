import { useState } from "react";
import { useHistory } from "react-router";

const Finalized = () => {
    const itemdetails = sessionStorage.getItem("items");
    const items = JSON.parse(itemdetails);
    const total = JSON.parse(sessionStorage.getItem("total"));
    sessionStorage.setItem("total",total);
    const details = sessionStorage.getItem("user");
    const user = JSON.parse(details);
    console.log(items);
    const history=useHistory(); 

    const handleSubmit=(e)=>{
        e.preventDefault();
        const name = user.name;
        const address = user.address;
        const order = {name,address,items};
        fetch('http://localhost:8086/order',{
            method:'POST',
            headers:{"Content-Type":"application/json"},
            body :JSON.stringify(order)
        }).then((res) =>{
            if(!res.ok)
                console.log(res);
            return res.json();
        }).then((data) =>{
            sessionStorage.setItem("order",JSON.stringify(data));
        });
        console.log("Order Placed");
        history.push("/Details"); 
    }

    return ( 
        <div className="finalized">
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
            </table>
            <h2>Total : {total}</h2>
            <h2>Address to be delivered : {user.address}</h2>
            <button onClick={handleSubmit} className="submit">Place Order</button>
        </div>
     );
}
 
export default Finalized;