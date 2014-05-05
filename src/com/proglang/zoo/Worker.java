package com.proglang.zoo;

// TO DO: Worker is currently an ordinary class.
// You will need to modify it to make it a task,
// so it can be given to an Executor thread pool.
//
public class Worker implements Runnable{
    private static final int A = constants.A;
    private static final int Z = constants.Z;
    private static final int numLetters = constants.numLetters;

    private Account[] accounts;
    private AccountCache[] mCachedAccounts;
    private String transaction;

    // TO DO: The sequential version of Worker peeks at accounts
    // whenever it needs to get a value, and opens, updates, and closes
    // an account whenever it needs to set a value.  This won't work in
    // the parallel version.  Instead, you'll need to cache values
    // you've read and written, and then, after figuring out everything
    // you want to do, (1) open all accounts you need, for reading,
    // writing, or both, (2) verify all previously peeked-at values,
    // (3) perform all updates, and (4) close all opened accounts.

    public Worker(Account[] allAccounts, String trans) {
        accounts = allAccounts;
        mCachedAccounts = new AccountCache[numLetters];
        transaction = trans;
    }

    // TO DO: parseAccount currently returns a reference to an account.
    // You probably want to change it to return a reference to an
    // account *cache* instead.

    private AccountCache parseAccount(String name) {
        int accountNum = (int) (name.charAt(0)) - (int) 'A';
        if (accountNum < A || accountNum > Z)
            throw new InvalidTransactionError();
        if(mCachedAccounts[accountNum] == null){
        	mCachedAccounts[accountNum] = initCacheAccount(mCachedAccounts[accountNum], accountNum);
        }
        AccountCache a = mCachedAccounts[accountNum];
        for (int i = 1; i < name.length(); i++) {
            if (name.charAt(i) != '*')
                throw new InvalidTransactionError();
            accountNum = (cachePeek(mCachedAccounts[accountNum], accountNum) % numLetters);
            a = mCachedAccounts[accountNum];
        }
        return a;
    }

    private int parseAccountOrNum(String name) {
        int rtn;
        if (name.charAt(0) >= '0' && name.charAt(0) <= '9') {
            rtn = new Integer(name).intValue();
        } else {
            //rtn = parseAccount(name).peek();
        	int index = (int) (name.charAt(0)) - (int) 'A';
        	rtn = cachePeek(parseAccount(name), index);
        }
        return rtn;
    }

    public void run() {
        // tokenize transaction
        String[] commands = transaction.split(";");

        for (int i = 0; i < commands.length; i++) {
            String[] words = commands[i].trim().split("\\s");
            if (words.length < 3)
                throw new InvalidTransactionError();
            AccountCache lhs = parseAccount(words[0]);
            if (!words[1].equals("="))
                throw new InvalidTransactionError();
            int rhs = parseAccountOrNum(words[2]);
            for (int j = 3; j < words.length; j+=2) {
                if (words[j].equals("+"))
                    rhs += parseAccountOrNum(words[j+1]);
                else if (words[j].equals("-"))
                    rhs -= parseAccountOrNum(words[j+1]);
                else
                    throw new InvalidTransactionError();
            }
            lhs.setValue(rhs);
        }
        commitCachedTransactions();
        System.out.println("commit: " + transaction);
    }

    private int cachePeek(AccountCache accountCache, int index){
    	if(accountCache == null){
    		accountCache = initCacheAccount(accountCache, index);
    	}else if (!accountCache.hasBeenCached()){
    		accountCache.setValue(accounts[index].peek());
    	}
    	return accountCache.getValue();
    }
    
    private AccountCache initCacheAccount(AccountCache accountCache, int index){
    	return new AccountCache(accounts[index].peek());
    }

    private void commitCachedTransactions(){
    	for (int i = 0; i < numLetters; i++){
    		if (mCachedAccounts[i] != null){
	    		if (mCachedAccounts[i].hasBeenWriten()) {
		    		try {
					    accounts[i].open(true);
					} catch (TransactionAbortException e) {
					    // won't happen in sequential version
					}
		    		accounts[i].update(mCachedAccounts[i].getValue());
		    		accounts[i].close();
	    		}
    		}
    	}
    }
}