package com.gooeywars.exception;

public class TagSameException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagSameException(){
		
	}
	
	public TagSameException(String message){
		super(message);
	}
}
