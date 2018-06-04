let express = require('express');
let bodyParser = require('body-parser');

let Config = require('./config');

let app = express();

app.use(bodyParser.json());

app.all('*', (req, res, next) => {
	res.header('Access-Control-Allow-Origin', '*');
	res.header('Access-Control-Allow-Headers', '*');
	res.header('Access-Control-Allow-Methods', '*');
	next();
});

let userRouter = require('./routes/user');
app.use('/user', userRouter);

let port = Config.port;
app.listen(port);
console.log('listen port %d', port);