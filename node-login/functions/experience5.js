'use strict';

const user = require('../models/user');
const nodemailer = require('nodemailer');
const config = require('../config/config.json');

exports.updateExperience5 = (email, score) => 

	new Promise((resolve, reject) => {

		user.find({ email: email })

		.then(users => {

			let user = users[0];
			const experience5 = user.experience5;

			const newExperience5 = experience5 + score;

			const newLevel = user.level;

			
			if (newExperience5 >= 100){
				//user.experience5 = newExperience5 - 100;
				//user.level = newLevel + 1;
				user.experience5 = 100;
				return user.save();
			} else {
				user.experience5 = newExperience5;
				user.level = newLevel;
				return user.save();
			}


		})

		.then(user => resolve({ status: 200, message: 'Experience Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});