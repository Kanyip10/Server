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

			const newLevel = user.level;

			
			if (newExperience >= 100){
				user.experience = newExperience - 100;
				user.level = newLevel + 1;
				return user.save();
			} else {
				user.experience = newExperience;
				user.level = newLevel;
				return user.save();
			}


		})

		.then(user => resolve({ status: 200, message: 'Experience Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});