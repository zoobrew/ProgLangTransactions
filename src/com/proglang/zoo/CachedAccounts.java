package com.proglang.zoo;

public class CachedAccounts {

	private static final int numLetters = constants.numLetters;
	private final Account[] mUncachedAccounts;
	private final AccountCache[] mCachedAccounts;


	public CachedAccounts(Account[] allAccounts){
		mUncachedAccounts = allAccounts;
		mCachedAccounts = new AccountCache[numLetters];
	}


	public int peek(int account){
		if (!(mCachedAccounts[account].hasBeenCached())){
			mCachedAccounts[account] = new AccountCache(mUncachedAccounts[account].peek());
		}
		return mCachedAccounts[account].getValue();
	}
}
