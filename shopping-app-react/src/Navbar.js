import {Link} from 'react-router-dom';
const Navbar = () => {
    return ( 
        <nav className="navbar">
        <h1>Shopping Portal</h1>
        <div className="links">
          <Link to="/" style={{color: 'white'}}>Login</Link>
          <Link to="/SignUp" style={{color: 'white'}}>SignUp</Link>
          <Link to="/" style={{color: 'white'}}>Log Out</Link>
        </div>
      </nav>
     );
}
 
export default Navbar;