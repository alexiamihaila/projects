import { React, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function AddDevice() {
  const [device, setDevice] = useState({
    description: '',
    address: '',
    consumption: '',
    id_user: ''
  });

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    // Retrieve token from local storage
    const token = localStorage.getItem('accessToken');

    // Include the token in the request headers
    axios.post('http://localhost:3003/spring-demo/admin/devices/addDevice', device, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .then(res => {
        navigate('/admin/adminPage');
      })
      .catch(error => console.log(error));
  };

  return (
    <div className="update-user-container">
      <div className="form-container">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <input
              type="text"
              name="description"
              className="form-control"
              placeholder="Enter Description"
              value={device.description} onChange={e => setDevice({ ...device, description: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="address">Address</label>
            <input type="text" name="address" className="form-control"
              placeholder="Enter Address"
              value={device.address} onChange={e => setDevice({ ...device, address: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="maxconsumption">Max Consumption/Hour</label>
            <input type="text" name="maxconsumption" className="form-control"
              placeholder="Enter max consumption"
              value={device.consumption} onChange={e => setDevice({ ...device, consumption: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="owner">Device Owner</label>
            <input type="text" name="owner" className="form-control"
              placeholder="Enter Owner"
              value={device.id_user} onChange={e => setDevice({ ...device, id_user: e.target.value })} />
          </div>
          <button className="btn btn-info">Save</button>
        </form>
      </div>
    </div>
  );
}

export default AddDevice;
