'use strict';


const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const tagSchema = mongoose.Schema(
	{
		name: {
			type: String,
			required: true,
			unique: true
		},
		description: {
			type: String,
			required: true
		},
		topics: [{ type: String }],
		sample: {
			type: String,
			required: true
		},
	},
	{
		collection: 'tags'
	}
);
mongoose.Promise = global.Promise;
//mongoose.connect('mongodb://admin:1234@ds145868.mlab.com:45868/test2');

module.exports = mongoose.model('tags', tagSchema);        