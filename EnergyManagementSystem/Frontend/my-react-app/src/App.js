import { BrowserRouter,Routes,Route } from "react-router-dom";
import Login from "./Login";
import Admin from "./Admin";
import User from "./User";
import UpdateUser from "./UpdateUser"
import UpdateDevice from "./UpdateDevice";
import AddUser from "./AddUser";
import AddDevice from "./AddDevice";
import ChartPage from "./ChartPage";
import UserSecond from "./UserSecond";
import ChatAdmin from "./ChatAdmin";

function App() {
  return (
    <div>
         <BrowserRouter>
            <Routes>
              <Route path="/" element={ <Login/>} />
              <Route path="/admin/adminPage" element={ <Admin/>} />
              <Route path="/userPage" element={ <User/>} />
              <Route path='/admin/updateUser/:userId' element={ <UpdateUser/>} />
              <Route path='/admin/updateDevice/:deviceId' element={ <UpdateDevice/> } />
              <Route path = '/admin/addUser' element = { <AddUser/>} />
              <Route path = '/admin/addDevice' element = { <AddDevice/>} />
              <Route path = '/chart' element = { <ChartPage/>} />
              <Route path = '/userPage2' element = { <UserSecond/>} />
              <Route path = '/chatAdmin' element = {<ChatAdmin/>} />
          
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
