// Import required modules
import express, { json, urlencoded } from 'express';
import { config } from 'dotenv';


import connectToMongoDB from './db.js';
connectToMongoDB();

// Configure dotenv
config();

const app = express();

// Define a route
app.get('/', (req, res) => {
  res.send('Hello World!');
});
// Add middleware
app.use(json()); // for parsing application/json
app.use(urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

import Disease from './Diseases.js';

app.post('/diseases', async (req, res) => {
    try {
      const disease = new Disease(req.body);
      await disease.save();
      res.status(201).send(disease);
    } catch (error) {
      console.log(error);
      res.status(400).send(error);
    }
  });

app.get('/diseases/:name', async (req, res) => {
    try {
      const disease = await Disease.findOne({ name: req.params.name });
      if (!disease) {
        return res.status(404).send({ message: 'Disease not found' });
      }
      res.send(disease.exercises);
    } catch (error) {
      res.status(500).send({ message: 'Server error' });
    }
});



// Start the server
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});