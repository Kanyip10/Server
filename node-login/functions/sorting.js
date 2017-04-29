'use strict';


const Tag = require('../models/tagSchema');

exports.sort = sort =>
	

new Promise((resolve,reject) => {

	 Tag.find({name:sort},{name:1, description:1,sample:1,_id:0})

	 
	
		.then(tag => resolve(tag[0]))

		.catch(err => 
								
				reject({ status: 500, message: 'Internal Server Error !' }))
			});
			
		













