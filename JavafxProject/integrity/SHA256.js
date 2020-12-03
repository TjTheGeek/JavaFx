const crypto = require('crypto');

const secret = 'abc';

const hash = crypto.createHash("sha256")
                   .update(secret)
                   .digest('hex');

console.log(hash);