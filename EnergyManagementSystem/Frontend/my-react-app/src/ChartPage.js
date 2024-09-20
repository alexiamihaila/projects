import * as React from 'react';
import { useState } from 'react';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';  // Import LocalizationProvider
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';  // Import AdapterDayjs
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';

function ChartPage() {
  const [selectedDate, setSelectedDate] = useState(null);

  const handleDateChange = (date) => {
    if (date.isValid()) {
      const jsDate = date.toDate(); 
      setSelectedDate(jsDate);
  
      const formattedDate = jsDate.toISOString().split('T')[0];
      console.log('Selected Date:', formattedDate);
    } else {
      console.error('Invalid date object:', date);
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DateCalendar onChange={handleDateChange} />
      </LocalizationProvider>
    </div>
  );
}

export default ChartPage;
