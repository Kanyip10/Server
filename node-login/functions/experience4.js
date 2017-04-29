'use strict';

const user = require('../models/user');
const nodemailer = require('nodemailer');
const config = require('../config/config.json');

exports.updateExperience4 = (email, score) => 

	new Promise((resolve, reject) => {

		user.find({ email: email })

		.then(users => {

			let user = users[0];
			const experience4 = user.experience4;

			const newExperience4 = experience4 + score;

			const newLevel = user.level;

			
			if (newExperience4 >= 100){
				//user.experience4 = newExperience4 - 100;
				//user.level = newLevel + 1;
				user.experience4 = 100;
				return user.save();
			} else {
				user.experience4 = newExperience4;
				user.level = newLevel;
				return user.save();
			}


		})

		.then(user => resolve({ status: 200, message: 'Experience Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});