const crypto = require('crypto');

const secret = 'a1b2c3';

const hash = crypto.createHmac('sha256', secret)
                   .update('Help me')
                   .digest('hex');

console.log(hash);