import { useState } from "react";
import useFetch from "./useFetch";
import { useHistory } from "react-router";

const Main = () => {
    const {data : products} = useFetch('http://localhost:8086/products');
    const {data : items} = useFetch('http://localhost:8086/items');
    const [quantity, setQuantity] = useState(0);
    const [productId, setProductId] = useState(1);
    const history=useHistory(); 

    const handleSubmit=(e)=>{
        e.preventDefault();
        const purchase={productId,quantity};
        var purchaseList = JSON.parse(sessionStorage.getItem("items"));
        if(purchaseList == null)
            purchaseList = [];
        purchaseList.push(purchase);
        console.log(purchaseList);
        sessionStorage.setItem("items",JSON.stringify(purchaseList));
        fetch('http://localhost:8086/addItem',{
            method:'POST',
            headers:{"Content-Type":"application/json"},
            body :JSON.stringify(purchase)
        });
        console.log("Item added to card");
        history.push("/Proceed"); 
    }

    return ( 
        <div className="mainclass">
            <br></br><br></br>
            <table border="1" className="table">
            <tr>
                <th>Product ID</th>
                <th>Description</th>
                <th>Product Name</th>
                <th>Price</th>
                <th>Available Quantity</th>
            </tr>
                {products && products.map((product) => (
                    <tr>
                        <td>{product.id}</td>
                        <td>{product.description}</td>
                        <td>{product.name}</td>
                        <td>{product.price}</td>
                        {items && items.map((item) => 
                            <td>{item.availableQuantity}</td>
                        )}
                    </tr>
                ))}
            </table><br></br>
            <form className="order" onSubmit={handleSubmit}>
                <label>Product Id : </label>
                <select
                value={productId}
                onChange={(e)=>setProductId(e.target.value)}
                >
                    {products && products.map((item) => (
                        <option value={item.id}>{item.id}</option>
                    ))}
                </select>
                <label>Quantity : </label>
                <input 
                type="text"
                value={quantity}
                onChange={(e)=>setQuantity(e.target.value)}
                /><br></br><br></br>
                <button className="submit">Add Purchase</button>
           </form>
        </div>
     );
}
 
export default Main;