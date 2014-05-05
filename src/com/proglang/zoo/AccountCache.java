package com.proglang.zoo;


//TO DO: you are not permitted to modify class Account
//
public class AccountCache {

	private int mValue;
	private int mOrigValue;
	private boolean mHasBeenRead = false;
	private boolean mHasBeenWriten = false;

    public AccountCache(int initialValue){
    	mValue = initialValue;
    	mOrigValue = initialValue;
    	mHasBeenRead = true;
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
