require("dotenv").config();
const  USER_NAME  =  process.env.USER_NAME;
const  PASSWORD  =  process.env.PASSWORD;
const request = require('request');
const base64 = require('base-64');
const URL_PREFIX = 'https://api.bitbucket.org/2.0';
console.log(USER_NAME)
console.log(PASSWORD)
console.log(base64.encode(USER_NAME +':' + PASSWORD))
const headers = {
    Authorization: 'Basic ' + base64.encode(USER_NAME +
        ':' + PASSWORD)
};
request.get(
    `${URL_PREFIX}/user`,
    {
        headers: headers
    },
    (err, res, body) => {
        console.log(JSON.parse(body));
    }
);
console.log("end of file")