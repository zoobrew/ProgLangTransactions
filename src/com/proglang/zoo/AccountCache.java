package com.proglang.zoo;


//TO DO: you are not permitted to modify class Account
//
public class AccountCache {

	private int mValue;
	private boolean mHasBeenRead = false;
	private boolean mHasBeenWriten = false;

    public AccountCache(int initialValue){
    	mValue = initialValue;
    	mHasBeenRead = true;
    }

    public AccountCache(){
    }

    public int getValue(){
    	mHasBeenRead = true;
    	return mValue;
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