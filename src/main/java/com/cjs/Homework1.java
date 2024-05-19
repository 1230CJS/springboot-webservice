package com.cjs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Homework1 {

    private static int NUMBER_VALUE_LENGTH = 3;

    public static void main(String[] args) {

        String given = args[0];
        String input = args[1];
        String result = "";
        System.out.println("(INPUT) Given "+given +", Input "+input);

        boolean isContinue = checkValue(given, input);
        System.out.println("isContinue:"+isContinue);
        if(!isContinue){
            System.out.println("(Output) Error INPUT Value Check");
            return;
        }else{
            result = compareValue(given, input);
        }

        System.out.println("(Output) "+result);
    }

    private static boolean checkValue(String strGivenNumber, String strInputNumber){

        boolean result1 = checkNumber(strGivenNumber, NUMBER_VALUE_LENGTH);
        boolean result2 = checkNumber(strInputNumber, NUMBER_VALUE_LENGTH);

        boolean result3 = checkDuplication(strGivenNumber, NUMBER_VALUE_LENGTH);
        boolean result4 = checkDuplication(strInputNumber, NUMBER_VALUE_LENGTH);

        return result1 && result2 && result3 && result4;
    }

    private static boolean checkNumber(String strNumber, int length){
        boolean result = true;
        try{
            if(strNumber != null && strNumber.length() == length){
                Integer.parseInt(strNumber);
            }else{
                result = false;
            }
        }catch (Exception e){
            result = false;
        }

        System.out.println("checkNumber strNumber:"+strNumber +", result:"+result);
        return result;
    }

    private static boolean checkDuplication(String strNumber, int length){
        List<String> strNumberList = Arrays.asList(strNumber.split(""));
        Set<String> strNumberSet = new HashSet<>(strNumberList);
        System.out.println("checkDuplication strNumberList:"+strNumberList);
        System.out.println("checkDuplication strNumberSet:"+strNumberSet.size());
        return strNumberSet.size() == length;
    }

    private static int[] changeNumber(String strNumber){
        int[] numbers = new int[strNumber.length()];
        for(int i=0; i<strNumber.length(); i++)
            numbers[i] = Integer.parseInt(String.valueOf(strNumber.charAt(i)));
        return numbers;
    }

    private static String compareValue(String strGivenNumber, String strInputNumber){
        int result_strike = 0;
        int result_ball = 0;
        String result = "";
        int[] givenNumbers = changeNumber(strGivenNumber);
        int[] inputNumbers = changeNumber(strInputNumber);

        for(int i = 0; i < givenNumbers.length; i++) {
            for (int j = 0; j < inputNumbers.length; j++) {
                if (givenNumbers[i] == inputNumbers[j] && i == j) {
                    result_strike++;
                } else if (givenNumbers[i] == inputNumbers[j] && i != j) {
                    result_ball++;
                }
            }
        }

        if(result_strike > 0){
            result += result_strike+"S";
        }
        if(result_ball > 0){
            result += result_ball+"B";
        }
        if(result_strike == 0 && result_ball == 0){
            result = "null";
        }

        System.out.println("compareValue result_strike:"+result_strike +", result_ball:"+result_ball);
        System.out.println("compareValue result:"+result);
        return result;
    }


}
