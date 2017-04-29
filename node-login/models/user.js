'use strict';

const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const userSchema = mongoose.Schema({ 

	name 			: String,
	email			: String, 
	hashed_password	: String,
	created_at		: String,
	temp_password	: String,
	temp_password_time: String,
	level			: {type: Number, min : 1, default:1},
	experience		: {type: Number, min:0, max:100, default: 0},
	experience2		: {type: Number, min:0, max:100, default: 0},
	experience3		: {type: Number, min:0, max:100, default: 0},
	experience4		: {type: Number, min:0, max:100, default: 0},
	experience5		: {type: Number, min:0, max:100, default: 0}
	
});

mongoose.Promise = global.Promise;


module.exports = mongoose.model('user', userSchema);        