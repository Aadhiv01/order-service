import './App.css';
import {BrowserRouter as Router,Route,Switch} from 'react-router-dom';
import Login from './Login';
import SignUp from './SignUp';
import Navbar from './Navbar';
import Main from './Main';
import Proceed from './Proceed';
import Finalized from './Finalized';
import Details from './Details';

function App() {
  return (
    <Router>
      <div className="App">
        <div className="content">
          <Navbar />
          <Switch>
            <Route exact path="/">
              <Login/>
            </Route>
            <Route exact path="/SignUp">
              <SignUp/>
            </Route>
          </Switch>
        </div>
        <div className="maincontent">
          <Switch>
            <Route exact path="/Main">
              <Main/>
            </Route>
            <Route exact path="/Proceed">
              <Proceed/>
            </Route>
            <Route exact path="/Finalized">
              <Finalized/>
            </Route>
            <Route exact path="/Details">
              <Details/>
            </Route>
          </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;
