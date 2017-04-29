'use strict';

const user = require('../models/user');
const nodemailer = require('nodemailer');
const config = require('../config/config.json');

exports.updateExperience3 = (email, score) => 

	new Promise((resolve, reject) => {

		user.find({ email: email })

		.then(users => {

			let user = users[0];
			const experience3 = user.experience3;

			const newExperience3 = experience3 + score;

			const newLevel = user.level;

			
			if (newExperience3 >= 100){
				//user.experience3 = newExperience3 - 100;
				//user.level = newLevel + 1;
				user.experience3 = 100;
				return user.save();
			} else {
				user.experience3 = newExperience3;
				user.level = newLevel;
				return user.save();
			}


		})

		.then(user => resolve({ status: 200, message: 'Experience Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});