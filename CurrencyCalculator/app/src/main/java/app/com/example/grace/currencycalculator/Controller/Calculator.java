package app.com.example.grace.currencycalculator.controller;


import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;

public class Calculator {

    private Validator expressionValidator;
    private ExpressionAnalyzer expressionAnalyzer;
    private String previousOperator = "";
    private String currentOperator = "";
    private String currentOperandString = "";
    private String firstExpressionPart="";
    private double previousOperand = 0.0;
    private double currentOperand = 0.0;
    private double computedValue = 0.0;
    private double previousExpressionValue = 1;
    private double previousExpValue = 0;
    private double nonPrecedenceComputedValue = 0;
    private boolean nonPrecedence = false;
    double operandBeforePrecedence = 0;

    public double compute(Expression expression) {
        expressionAnalyzer = new ExpressionAnalyzer();
        expressionValidator = new Validator();

        List < ExpressionPart > expressionParts = expression.getExpressionParts();
        firstExpressionPart = expressionParts.get(0).getValue();

        if(firstExpressionPart.contains("(")) {
           currentOperandString = expressionValidator.removeBrackets(firstExpressionPart);
            computedValue = 0.0;
            analyzeExpressionInParenthesis(expressionParts,0);
        }
        else
                computedValue = Double.parseDouble(expressionParts.get(0).getValue());

        for(int index = 1; index < expressionParts.size(); index++) {

            ExpressionPart currentExpressionPart = expressionParts.get(index);
            ExpressionPart previousExpressionPart = expressionParts.get(index-1);

            if(currentExpressionPart.isOperand()) {

                currentOperandString = currentExpressionPart.getValue();
                currentOperator = previousExpressionPart.getValue();

                if ((currentOperandString).startsWith("(")) {
                    currentOperandString = expressionValidator.removeBrackets(currentOperandString);
                    currentExpressionPart.setValue(currentOperandString);
                    analyzeExpressionInParenthesis(expressionParts,index);
                }
                else {
                    currentOperand = Double.parseDouble(currentOperandString);
                }
                computedValue = getTemporaryValue();
            }
        }
        clear();
        return computedValue;
    }

    private void analyzeExpressionInParenthesis(List<ExpressionPart> expressionParts,int currentIndex) {

        double computedValueBuffer = computedValue;
        String currentOperatorBuffer = currentOperator;
        Expression expression = expressionAnalyzer.breakDownExpression(currentOperandString);
        currentOperand = compute(expression);
        expressionParts.set(currentIndex, new Operand(currentOperand + ""));

        if(computedValueBuffer != 0) {
            computedValue = computedValueBuffer;
        }
        currentOperator = currentOperatorBuffer;
    }

    private double getTemporaryValue() {
        switch (currentOperator) {

            case "+":
                operandBeforePrecedence = computedValue;
                computedValue = computedValue + currentOperand;
                previousExpValue = 1;
                updatePrecedence();
                break;

            case "-":
                operandBeforePrecedence = computedValue;
                computedValue = computedValue - currentOperand;
                previousExpValue = -1;
                updatePrecedence();
                break;

            case "*":
            case "/":
              computedValue = evaluatePrecedence();
                break;
        }
        resetPrevious();
        return computedValue;
    }

    private double evaluatePrecedence() {

        double expressionValue;

        switch (currentOperator) {

            case "*":
                if(!previousOperator.isEmpty() ){
                        expressionValue = previousOperand * currentOperand;
                        computedValue =  adjustComputedValue(expressionValue);
                } else {
                computedValue = computedValue * currentOperand;
            }
                break;

            case "/":
                if(!previousOperator.isEmpty()){
                    expressionValue = previousOperand / currentOperand;
                    computedValue = adjustComputedValue(expressionValue);
                }
                else {
                    computedValue = computedValue / currentOperand;
                }
                break;
        }
        resetPrevious();
        return computedValue;
    }

    private double adjustComputedValue(double expressionValue) {

        if (previousOperator.equals("+")) {
            computedValue = (computedValue - previousOperand) + expressionValue;

        } else if (previousOperator.equals("-")) {
            computedValue = (computedValue + previousOperand) - expressionValue;
        }
        else {
            switch (currentOperator) {
                case "*":
                   getAdjustedMultiplication();
                    break;
                case "/":
                    getAdjustedDivision();
                    break;
            }
        }
        previousExpressionValue = previousExpValue * expressionValue;
        nonPrecedence = false;
        return computedValue;
    }

    private void updatePrecedence() {
        nonPrecedenceComputedValue = computedValue;
        nonPrecedence = true;
    }

    private void resetPrevious() {
        previousOperand = currentOperand;
        previousOperator = currentOperator;
    }

    private double getAdjustedDivision() {
        if(operandBeforePrecedence != 0) {
            computedValue = previousExpressionValue / currentOperand + operandBeforePrecedence;
        } else
            computedValue = computedValue / currentOperand;
        return computedValue;
    }

    private double getAdjustedMultiplication() {
        if(operandBeforePrecedence != 0) {
            computedValue = previousExpressionValue * currentOperand + operandBeforePrecedence;
        } else
            computedValue = computedValue * currentOperand;
        return computedValue;
    }



    private void clear() {
        previousOperand = 0.0;
        previousOperator = "";
        currentOperator = "";
        currentOperandString = "";
        currentOperand = 0.0;
        previousExpressionValue = 0.0;
        previousExpValue = 1;
        nonPrecedence = false;
        previousExpressionValue = 0;
        previousExpValue = 0;
        firstExpressionPart = "";
        expressionAnalyzer = new ExpressionAnalyzer();
        operandBeforePrecedence = 0;


    }


}
