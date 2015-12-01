package app.com.example.grace.currencycalculator.Controller;

import junit.framework.TestCase;

public class ValidatorTest extends TestCase {

    Validator validator;
    public void setUp() throws Exception {
        super.setUp();
        validator = new Validator();

    }

    public void testValidate() throws Exception {

    }

    public void testValidateOperatorWhenThePreviousExpressionPartIsAnOperator () throws Exception {
        String expression = "3+2+";
        validator.setExpression(expression);
        assertEquals("3+2*",validator.validateOperator('*'));
    }

    public void testValidateOperatorWhenThePreviousExpressionPartIsNotAnOperator() throws Exception {
        String expression = "13";
        validator.setExpression(expression);
        assertEquals("13*",validator.validateOperator('*'));
    }

    public void testDivisionByZeroWhenKeyPressGeneratesDivisionByZeroException() throws Exception {
        String expression = "5/";
        validator.setExpression(expression);
        assertTrue(validator.isDivisionByZero('0'));
    }

    public void testDivisionByZeroWhenKeyPressDoesNotGeneratesDivisionByZeroException() throws Exception {
        String expression = "5+3";
        validator.setExpression(expression);
        assertFalse(validator.isDivisionByZero('0'));
    }

    public void testRepeatedZerosWhenZerosAreRepeated() throws Exception {
        String expression = "5+0";
        validator.setExpression(expression);
        assertTrue(validator.isRepeatedZeros('0'));
    }
    public void testRepeatedZerosWhenZerosAreRepeatedButIsValid() throws Exception {
        String expression = "5+30";
        validator.setExpression(expression);
        assertFalse(validator.isRepeatedZeros('0'));
    }
    public void testRepeatedZerosWhenZerosAreNotRepeated() throws Exception {
        String expression = "32+3";
        validator.setExpression(expression);
        assertFalse(validator.isRepeatedZeros('0'));
    }

}