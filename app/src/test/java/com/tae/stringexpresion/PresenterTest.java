package com.tae.stringexpresion;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eduardo on 26/04/2016.
 */
public class PresenterTest {


    private static String METHOD_CALCULATE = "calculate";
    private static String METHOD_MATCH = "match";
    private PresenterImpl presenter;
    private Method calculate, match;
    private Class matchType;
    private Class[] caculateTypes;
    private String[] calculateParams;


    @Before
    public void setUp()throws Exception{
        presenter = new PresenterImpl();

        caculateTypes = new Class[3];
        caculateTypes[0] = String.class;
        caculateTypes[1] = String.class;
        caculateTypes[2] = String.class;
        calculate = presenter.getClass().getDeclaredMethod(METHOD_CALCULATE, caculateTypes);
        calculate.setAccessible(true);
        calculateParams = new String[3];

        matchType = Matcher.class;
        match = presenter.getClass().getDeclaredMethod(METHOD_MATCH, matchType);
        match.setAccessible(true);

    }

    @Test
    public void presenter_ShouldBeInitialize() throws Exception {
        assertNotNull(presenter);
    }

    @Test
    public void match_ShouldMatch() throws Exception {
        String input = "5+5";
        Pattern number = Pattern.compile("([0-9]*)");
        Pattern operator = Pattern.compile("([-+*/])");
        Matcher matcherNumber = number.matcher(input);
        Matcher matcherOperator = operator.matcher(input);

        List<String> numList = (List<String>) match.invoke(presenter, matcherNumber);
        assertTrue(numList.size() > 0);

        List<String> opList = (List<String>) match.invoke(presenter, matcherOperator);
        assertTrue(opList.size() > 0);
    }

    @Test
    public void calculate_ShouldReturnSumValue() throws Exception {
        calculateParams[0] = "5.5";
        calculateParams[1] = "5";
        calculateParams[2] = "+";
        double result = (double) calculate.invoke(presenter, calculateParams);
        assertThat(result, is(equalTo(10.5)));
    }

    @Test
    public void calculate_ShouldReturnSubstactionValue() throws Exception {
        calculateParams[0] = "5";
        calculateParams[1] = "5";
        calculateParams[2] = "-";
        double result = (double) calculate.invoke(presenter, calculateParams);
        assertThat(result, is(equalTo(0.0)));
    }

    @Test
    public void calculate_ShouldReturnMultiplicationValue() throws Exception {
        calculateParams[0] = "5";
        calculateParams[1] = "5";
        calculateParams[2] = "*";
        double result = (double) calculate.invoke(presenter, calculateParams);
        assertThat(result, is(equalTo(25.0)));
    }

    @Test
    public void calculate_ShouldReturnDivisionValue() throws Exception {
        calculateParams[0] = "25";
        calculateParams[1] = "5";
        calculateParams[2] = "/";
        double result = (double) calculate.invoke(presenter, calculateParams);
        assertThat(result, is(equalTo(5.0)));
    }


}
