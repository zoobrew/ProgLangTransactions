package com.proglang.zoo;


//TO DO: you are not permitted to modify class Account
//
public class AccountCache {

	private int mValue;
	private int mOrigValue;
	private int mIndex;
	private boolean mHasBeenRead = false;
	private boolean mHasBeenWriten = false;

    public AccountCache(int initialValue, int index){
    	mValue = initialValue;
    	mOrigValue = initialValue;
    	mHasBeenRead = true;
    	mIndex = index;
    }

    public AccountCache(){
    }

    /**
     * get the working cache value
     */
    public int getValue(){
    	mHasBeenRead = true;
    	return mValue;
    }
    
    /**
     * get the value that was peeked
     * when the cache was created.
     */
    public int getOrigValue(){
    	return mOrigValue;
    }
    
    /**
     * get the index of the 
     * corresponding account
     */
    public int getIndex(){
    	return mIndex;
    }

    /** 
     * set the working cache value
     */
    public void setValue(int value){
    	mHasBeenWriten = true;
    	mValue = value;
    }

    /**
     * return if the value has been set
     */
    public boolean hasBeenCached(){
    	return mHasBeenRead;
    }
    
    /**
     * returns true if cached value has 
     * updated from the original value,
     * otherwise returns false;
     */
    public boolean hasBeenWriten(){
    	return mHasBeenWriten;
    }
}
