'use strict';

const auth = require('basic-auth');
const jwt = require('jsonwebtoken');

const register = require('./functions/register');
const login = require('./functions/login');
const profile = require('./functions/profile');
const password = require('./functions/password');
const sorting = require('./functions/sorting');
const config = require('./config/config.json');
const experience = require('./functions/experience');
const experience2 = require('./functions/experience2');
const experience3 = require('./functions/experience3');
const experience4 = require('./functions/experience4');
const experience5 = require('./functions/experience5');

module.exports = router => {

	router.get('/', (req, res) => res.end('Welcome to Learn2Crack !'));

	router.post('/authenticate', (req, res) => {

		const credentials = auth(req);

		if (!credentials) {

			res.status(400).json({ message: 'Invalid Request !' });

		} else {

			login.loginUser(credentials.name, credentials.pass)

			.then(result => {

				const token = jwt.sign(result, config.secret, { expiresIn: 1440 });
			
				res.status(result.status).json({ message: result.message, token: token });

			})

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	router.post('/users', (req, res) => {

		const name = req.body.name;
		const email = req.body.email;
		const password = req.body.password;

		if (!name || !email || !password || !name.trim() || !email.trim() || !password.trim()) {

			res.status(400).json({message: 'Invalid Request !'});

		} else {

			register.registerUser(name, email, password)

			.then(result => {

				res.setHeader('Location', '/users/'+email);
				res.status(result.status).json({ message: result.message })
			})

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	router.get('/users/:id', (req,res) => {

		if (checkToken(req)) {

			profile.getProfile(req.params.id)

			.then(result => res.json(result))

			.catch(err => res.status(err.status).json({ message: err.message }));

		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.put('/users/:id', (req,res) => {

		if (checkToken(req)) {

			const oldPassword = req.body.password;
			const newPassword = req.body.newPassword;

			if (!oldPassword || !newPassword || !oldPassword.trim() || !newPassword.trim()) {

				res.status(400).json({ message: 'Invalid Request test!' });

			} else {

				password.changePassword(req.params.id, oldPassword, newPassword)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.put('/users/:id/experience', (req,res) => {

		if (checkToken(req)) {

			const email = req.params.id;
			const newExp = req.body.newExp;

			if (!newExp) {

				res.status(400).json({ message: 'Invalid Request 123!' });

			} else {

				experience.updateExperience(email, newExp)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.put('/users/:id/experience2', (req,res) => {

		if (checkToken(req)) {

			const email = req.params.id;
			const newExp = req.body.newExp;

			if (!newExp) {

				res.status(400).json({ message: 'Invalid Request 123!' });

			} else {

				experience2.updateExperience2(email, newExp)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.put('/users/:id/experience3', (req,res) => {

		if (checkToken(req)) {

			const email = req.params.id;
			const newExp = req.body.newExp;

			if (!newExp) {

				res.status(400).json({ message: 'Invalid Request 123!' });

			} else {

				experience3.updateExperience3(email, newExp)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.put('/users/:id/experience4', (req,res) => {

		if (checkToken(req)) {

			const email = req.params.id;
			const newExp = req.body.newExp;

			if (!newExp) {

				res.status(400).json({ message: 'Invalid Request 123!' });

			} else {

				experience4.updateExperience4(email, newExp)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.put('/users/:id/experience5', (req,res) => {

		if (checkToken(req)) {

			const email = req.params.id;
			const newExp = req.body.newExp;

			if (!newExp) {

				res.status(400).json({ message: 'Invalid Request 123!' });

			} else {

				experience5.updateExperience5(email, newExp)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.post('/users/:id/password', (req,res) => {

		const email = req.params.id;
		const token = req.body.token;
		const newPassword = req.body.password;

		if (!token || !newPassword || !token.trim() || !newPassword.trim()) {

			password.resetPasswordInit(email)

			.then(result => res.status(result.status).json({ message: result.message }))

			.catch(err => res.status(err.status).json({ message: err.message }));

		} else {

			password.resetPasswordFinish(email, token, newPassword)

			.then(result => res.status(result.status).json({ message: result.message }))

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	router.get('/ReferenceList/:id/position',(req,res) => {

		
		if (checkToken(req)) {

		const email = req.params.id;
		const sort = req.query.sort;

		sorting.sort(sort)

		.then(result => res.json(result))

			.catch(err => res.status(err.status).json({ message: err.message }));}

			else{

				res.status(401).json({ message: 'Invalid Token !' });
			}


	});

	function checkToken(req) {

		const token = req.headers['x-access-token'];

		if (token) {

			try {

  				var decoded = jwt.verify(token, config.secret);

  				return decoded.message === req.params.id;

			} catch(err) {

				return false;
			}

		} else {

			return false;
		}
	}
}