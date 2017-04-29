'use strict';

const user = require('../models/user');
const nodemailer = require('nodemailer');
const config = require('../config/config.json');

exports.updateExperience2 = (email, score) => 

	new Promise((resolve, reject) => {

		user.find({ email: email })

		.then(users => {

			let user = users[0];
			const experience2 = user.experience2;

			const newExperience2 = experience2 + score;

			const newLevel = user.level;

			
			if (newExperience2 >= 100){
				//user.experience2 = newExperience2 - 100;
				//user.level = newLevel + 1;
				user.experience2 = 100;
				return user.save();
			} else {
				user.experience2 = newExperience2;
				user.level = newLevel;
				return user.save();
			}


		})

		.then(user => resolve({ status: 200, message: 'Experience Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});