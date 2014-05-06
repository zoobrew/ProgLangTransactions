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

    public int getValue(){
    	mHasBeenRead = true;
    	return mValue;
    }
    
    public int getOrigValue(){
    	return mOrigValue;
    }
    
    public int getIndex(){
    	return mIndex;
    }

    public void setValue(int value){
    	mHasBeenWriten = true;
    	mValue = value;
    }

    public boolean hasBeenCached(){
    	return mHasBeenRead;
    }
    
    public boolean hasBeenWriten(){
    	return mHasBeenWriten;
    }
}
