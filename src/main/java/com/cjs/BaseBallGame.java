package com.cjs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseBallGame {

    private boolean isDebugMode = false;

    public BaseBallGame(){
        setDebugMode(false);
    }
    public BaseBallGame(boolean isDebug){
        setDebugMode(isDebug);
    }

    public String getScore(String given, String input){
        int numberDefaultLength = 3;
        String result = null;
        outPrintln("(INPUT) Given "+given +", Input "+input);

        boolean isContinue = checkValue(given, input, numberDefaultLength);
        logPrintln("isContinue:"+isContinue);
        if(!isContinue){
            result = "(Output) Error INPUT Value Check";
        }else{
            result = compareValue(given, input);
        }

        outPrintln("(Output) "+result);
        return result;
    }

    private void setDebugMode(boolean isDebug){
        isDebugMode = isDebug;
    }

    private void logPrintln(String str){
        if(isDebugMode){
            System.out.println(str);
        }
    }

    private void outPrintln(String str){
        System.out.println(str);
    }

    private boolean checkValue(String strGivenNumber, String strInputNumber, int numberLength){

        boolean result1 = checkNumber(strGivenNumber, numberLength);
        boolean result2 = checkNumber(strInputNumber, numberLength);

        boolean result3 = checkDuplication(strGivenNumber, numberLength);
        boolean result4 = checkDuplication(strInputNumber, numberLength);

        return result1 && result2 && result3 && result4;
    }

    private boolean checkNumber(String strNumber, int length){
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

        logPrintln("checkNumber strNumber:"+strNumber +", result:"+result);
        return result;
    }

    private boolean checkDuplication(String strNumber, int length){
        List<String> strNumberList = Arrays.asList(strNumber.split(""));
        Set<String> strNumberSet = new HashSet<>(strNumberList);
        logPrintln("checkDuplication strNumberList:"+strNumberList);
        logPrintln("checkDuplication strNumberSet:"+strNumberSet.size());
        return strNumberSet.size() == length;
    }

    private int[] changeNumber(String strNumber){
        int[] numbers = new int[strNumber.length()];
        for(int i=0; i<strNumber.length(); i++)
            numbers[i] = Integer.parseInt(String.valueOf(strNumber.charAt(i)));
        return numbers;
    }

    private String compareValue(String strGivenNumber, String strInputNumber){
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
            result = "(null)";
        }

        logPrintln("compareValue result_strike:"+result_strike +", result_ball:"+result_ball);
        logPrintln("compareValue result:"+result);
        return result;
    }
}
