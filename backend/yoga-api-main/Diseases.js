import { Schema, model } from 'mongoose';

const exerciseSchema = new Schema({
  name: {
    type: String,
    
  },
  imageLink: {
    type: String,
    
  }
});

const diseaseSchema = new Schema({
  name: {
    type: String,
  },
  exercises: [exerciseSchema]
});

const Disease = model('Disease', diseaseSchema);

export default Disease;