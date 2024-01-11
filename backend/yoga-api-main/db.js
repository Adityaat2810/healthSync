import express from 'express';
import { connect } from 'mongoose';
import { config } from 'dotenv';
// Configure dotenv
config();


// const mongoURI= 'mongodb+srv://aitools:irfan123@cluster0.1w1dr3f.mongodb.net/listdb?retryWrites=true&w=majority'
const mongoURI=process.env.URI
//install mongodb mongose express nodemon
//for nodeon in vite remove module in packagejson
//remove <> in username password
//?after this your databse naeme
async function connectToMongoDB() {
  try {
    await connect(mongoURI, { useNewUrlParser: true });
    console.log("Connected to MongoDB..at last");
   
    

}
  catch(err) {
    console.error("Error connecting to MongoDB:", err);
    throw err;
  }
}
export default connectToMongoDB;
