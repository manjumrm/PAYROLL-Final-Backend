package com.vetologic.payroll.util;

import java.util.Random;


public class RandomGen {
	public static int getRandom(int max){
		int min = 1;
	       // return (int) (Math.random()*max);  //incorrect always return zero
//	        return (int) (Math.random()*max);
		Random rand = new Random();

		 int randomNum = rand.nextInt((max - min) + 1) + min;
		 return randomNum;
	}

	private static String CHAR_LIST = "1234567890";
    private static final int RANDOM_STRING_LENGTH = 6;
     
    public String generateRandomString(){
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        //System.out.println("TEST   : "+randStr.toString());
        return randStr.toString();
    }
    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
 
   /* public static void main(String[] args) 
    {
        RandomGen msr=new RandomGen();
        System.out.println(msr.generateRandomString());
        System.out.println(msr.generateRandomString());
        System.out.println(msr.generateRandomString());
        System.out.println(msr.generateRandomString());
        System.out.println(msr.getRandomNumber());
    } */
}