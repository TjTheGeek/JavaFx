const crypto = require('crypto');

const secret = 'Security Principles';

const hash = crypto.createHmac('sha256', secret)
                   .update('Help me')
                   .digest('hex');
console.log(hash);