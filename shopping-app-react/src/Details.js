import useFetch from "./useFetch";
import {Link} from 'react-router-dom';

const Details = () => {
    const {data : items} = useFetch('http://localhost:8086/fetchItems');
    const {data : total} = useFetch('http://localhost:8086/fetchTotal');
    const {data : order} = useFetch('http://localhost:8086/fetchOrder');
    const details = sessionStorage.getItem("user");
    const user = JSON.parse(details);
    return ( 
        <div className="details">
            <h2>Purchase Details : </h2>
            <h3>Order ID : {order.id}</h3>
            <table border="1" className="table">
                <tr>
                    <th>Product ID</th>
                    <th>Product Price</th>
                    <th>Quantity</th>
                </tr>
                <tr>
                    {items && items.map((item) => (
                        <div className="itemlist">
                            <td>{item.productId}</td>
                            <td>{item.productPrice}</td>
                            <td>{item.quantity}</td>
                        </div>
                    ))}
                </tr>
            </table>
            <h2>Total : {total}</h2>
            <h2>Address to be delivered : {user.address}</h2><br></br><br></br>
            <Link to="/Main" style={{color: 'white'}}>New Purchase</Link>
        </div>
     );
}
 
export default Details;