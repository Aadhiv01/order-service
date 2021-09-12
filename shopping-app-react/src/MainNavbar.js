import {Link} from 'react-router-dom';
const MainNavbar = () => {
    const details = sessionStorage.getItem("user");
    let user = JSON.parse(details);
    if(user == null)
      user = ' ';
    return ( 
        <nav className="navbar">
        <h1>Welcome {user.name}</h1>
        <div className="links">
          <Link to="/Proceed" style={{color: 'white'}}>View Cart</Link>
          <Link to="/" style={{color: 'white'}}>Log Out</Link>
        </div>
      </nav>
     );
}
 
export default MainNavbar;