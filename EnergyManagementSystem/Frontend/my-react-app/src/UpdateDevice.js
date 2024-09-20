import React, { useEffect, useState } from "react";
import './Update.css'; 
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

async function fetchDataFromServer(url) {
  try {
    const token = localStorage.getItem('accessToken');
    const response = await axios.get(url, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

function UpdateDevice() {
  const { deviceId } = useParams();
  const navigate = useNavigate();

  const [device, setDevice] = useState({
    deviceId: deviceId,
    description: '',
    address: '',
    consumption: '',
    id_user: ''
  });

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetchDataFromServer(`http://localhost:3003/spring-demo/admin/getDeviceWithId/${deviceId}`);
        setDevice({
          ...device,
          description: response.description,
          address: response.address,
          consumption: response.consumption,
          id_user: response.id_user
        });
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, [deviceId]);

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.put(`http://localhost:3003/spring-demo/admin/devices/updateDevice/${deviceId}`, device, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      },
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
            <input
              type="text"
              name="address"
              className="form-control"
              placeholder="Enter Address"
              value={device.address} onChange={e => setDevice({ ...device, address: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="maxconsumption">Max Consumption/Hour</label>
            <input
              type="text"
              name="maxconsumption"
              className="form-control"
              placeholder="Enter max consumption"
              value={device.consumption} onChange={e => setDevice({ ...device, consumption: e.target.value })} />
          </div>
          <div className="form-group">
            <label htmlFor="owner">Device Owner</label>
            <input
              type="text"
              name="owner"
              className="form-control"
              placeholder="Enter Owner"
              value={device.id_user} onChange={e => setDevice({ ...device, id_user: e.target.value })} />
          </div>
          <button className="btn btn-info">Update</button>
        </form>
      </div>
    </div>
  );
}

export default UpdateDevice;
