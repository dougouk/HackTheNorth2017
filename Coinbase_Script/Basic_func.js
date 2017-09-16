console.log("Hello, World!");
var Client = require('coinbase').Client;

try {
    console.log(require.resolve("coinbase"));
} catch(e) {
    console.error("coinbase is not found");
    process.exit(e.code);
}

var client = new Client(
  {
    'apiKey':'MuQguig3m2ARAngf',
    'apiSecret': 'wVIdkjyuHLI1H2jkfoJdgdD6DYrMQTpm'
  }
);
/// Available accounts
var address = null;
// use an actual address rather than primar. 
client.getAccount('primary', function(err, account) {
  account.createAddress(null,function(err, addr) {
    console.log(addr);
    address = addr;
  });
});

newAccount();
checkAccount();
sendMoney();

// making my new wallet : only if no wallet before
function newAccount(){
  client.getAccounts({}, function(err, accounts) {
    if(accounts.length<=0){client.createAccount({'name': 'New Wallet'}, function(err, acct) {
  console.log(acct.name + ': ' + acct.balance.amount + ' ' + acct.balance.currency);
});}

  });
}

function sendMoney(){
  client.getAccount('primary', function(err, account) {
    account.sendMoney({'to': address,
                       'amount': '0.01',
                       'currency': 'BTC'}, function(err, tx) {
      console.log("waiitt " + tx);
      console.log("MONEY HAS BEEN SENT $$$$$ BLING");
    });
  });
}
function requestMoney(){

}


// check all the wallets
function checkAccount(){
  client.getAccounts({}, function(err, accounts) {
    if(accounts.length>0){    accounts.forEach(function(acct) {
          console.log('my bal: ' + acct.balance.amount + ' for ' + acct.name + 'id '+ acct.id);
        });}

  });
}
