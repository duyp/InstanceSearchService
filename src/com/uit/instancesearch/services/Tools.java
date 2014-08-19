package com.uit.instancesearch.services;

import java.util.Random;

public class Tools {
	
	private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
	private static final String ALLOWED_NUMBERS="0123456789";
	
	public static String getRandomString(final int sizeOfRandomString) {
	  final Random random=new Random();
	  final StringBuilder sb=new StringBuilder();
	  for(int i=0;i<sizeOfRandomString;++i)
	    sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
	  return sb.toString();
	}
	
	public static String getRandomNumberString(final int sizeOfRandomString) {
	  final Random random=new Random();
	  final StringBuilder sb=new StringBuilder();
	  for(int i=0;i<sizeOfRandomString;++i)
	    sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_NUMBERS.length())));
	  return sb.toString();
	}
	
}
