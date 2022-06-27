import { Button, Radio } from "antd";
import { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';

import { getAllStudents } from './client';



function App() {
  const [students, setStudents] = useState([]);

  const fetchStudents = () => getAllStudents().then(res => res.json())
  .then(data => setStudents(data));

  /**
   * Empty Array on second args mean zero dependencies
   */
  useEffect(() => {
    console.log("Component is mounted")
    fetchStudents();
  }, []);

  if(students.length <= 0){
    return "no data";
  }

  return (
    <div className="App">
     {students.map((student, index) => {
      return <p key={index}>{student.id} {student.name}</p>
     })}
    </div>
  );
}

export default App;
