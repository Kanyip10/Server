'use strict';

const user = require('../models/user');
const nodemailer = require('nodemailer');
const config = require('../config/config.json');

exports.updateExperience = (email, score) => 

	new Promise((resolve, reject) => {

		user.find({ email: email })

		.then(users => {

			let user = users[0];
			const experience = user.experience;

			const newExperience = experience + 5*score;

				user.experience = newExperience;

				return user.save();
		})

		.then(user => resolve({ status: 200, message: 'Experience Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});