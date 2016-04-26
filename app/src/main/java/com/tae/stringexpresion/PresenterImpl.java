package com.tae.stringexpresion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eduardo on 26/04/2016.
 */
public class PresenterImpl implements Presenter {

    private MainView mainView;

    @Override
    public void makeOperation(String input) {
        //patterns for numbers and operators
        Pattern number = Pattern.compile("([0-9]*\\.?[0-9])");
        Pattern operator = Pattern.compile("([-+*/])");
        //match numbers and operators
        Matcher matcherNumber = number.matcher(input);
        Matcher matcherOperator = operator.matcher(input);

        List<String> numList = match(matcherNumber);
        String[] arrayNum = new String[numList.size()];
        numList.toArray(arrayNum);
        List<String> operList = match(matcherOperator);

        double result = 0;
        for (int i = 0; i < arrayNum.length; i++) {
            if (i > 0) {
                String prev = arrayNum[i - 1];
                String current = arrayNum[i];
                String op = operList.get(i-1);
                result  = calculate(prev, current, op);
                arrayNum[i] = String.valueOf(result);
            }
        }
        mainView.showResult(String.valueOf(result));
    }

    @Override
    public void initMainView(MainActivity activity) {
        mainView = activity;
    }

    private List<String> match(Matcher matcherNumber) {
        List<String> list = new ArrayList<>();
        while(matcherNumber.find()) {
            String coincidence = matcherNumber.group().intern();
            if (!coincidence.isEmpty()) {
                list.add(coincidence);
            }
        }
        return list;
    }

    private double calculate(String prev, String current, String o) {
        double result = 0;
        switch (o) {
            case "+":
                result = Double.parseDouble(prev) + Double.parseDouble(current);
                break;
            case "-":
                result = Double.parseDouble(prev) - Double.parseDouble(current);
                break;
            case "*":
                result = Double.parseDouble(prev) * Double.parseDouble(current);
                break;
            case "/":
                result = Double.parseDouble(prev) / Double.parseDouble(current);
                break;
        }
        return result;
    }
}
