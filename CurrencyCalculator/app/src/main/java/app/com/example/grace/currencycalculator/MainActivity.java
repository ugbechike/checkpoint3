package app.com.example.grace.currencycalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import app.com.example.grace.currencycalculator.Controller.Validator;

public class MainActivity extends AppCompatActivity {
    private TextView computationArea;

    private TextView resultArea;

    private Button clearButton;

    private Button openBracketButton;

    private Button closeBracketButton;

    private ImageButton delButton;

    private Button sevenButton;

    private Button eightButton;

    private Button nineButton;

    private Button divisionButton;

    private Button fourButton;

    private Button fiveButton;

    private Button sixButton;

    private Button multiplicationButton;

    private Button oneButton;

    private Button twoButton;

    private Button threeButton;

    private Button minusButton;

    private Button zeroButton;

    private Button decimalButton;

    private Button equalsButton;

    private Button plusButton;

    private Button sourceCurrencyButton;

    private Button destinationCurrencyButton;

    private Validator expressionValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeComponents();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeComponents() {
        expressionValidator = new Validator();
        computationArea = (TextView)findViewById(R.id.computation_area);
        resultArea = (TextView)findViewById(R.id.result_area);
        clearButton = (Button) findViewById(R.id.clear);
        openBracketButton = (Button) findViewById(R.id.open_bracket);
        closeBracketButton = (Button) findViewById(R.id.close_bracket);
        delButton = (ImageButton)findViewById(R.id.del);
        sevenButton = (Button) findViewById(R.id.seven);

        eightButton = (Button) findViewById(R.id.eight);
        nineButton = (Button) findViewById(R.id.nine);
        divisionButton = (Button) findViewById(R.id.division);
        fourButton = (Button)findViewById(R.id.four);
        fiveButton = (Button) findViewById(R.id.five);

        sixButton = (Button) findViewById(R.id.six);
        multiplicationButton = (Button) findViewById(R.id.times);
        minusButton = (Button) findViewById(R.id.minus);
        delButton = (ImageButton)findViewById(R.id.del);
        zeroButton = (Button) findViewById(R.id.zero);

        decimalButton = (Button) findViewById(R.id.decimal);
        equalsButton = (Button) findViewById(R.id.equals);
        plusButton = (Button) findViewById(R.id.plus);
        sourceCurrencyButton = (Button) findViewById(R.id.source_currency);
        destinationCurrencyButton = (Button)findViewById(R.id.destination_currency);

    }

    public void display(View view) {

        switch(view.getId()) {
            case R.id.nine:
                updateWorkArea('9');
                break;
            case R.id.eight:
                updateWorkArea('8');
                break;
            case R.id.seven:
                updateWorkArea('7');
                break;
            case R.id.six:
                updateWorkArea('6');
                break;
            case R.id.five:
                updateWorkArea('5');
                break;
            case R.id.four:
                updateWorkArea('4');
                break;
            case R.id.three:
                updateWorkArea('3');
                break;
            case R.id.two:
                updateWorkArea('2');
                break;
            case R.id.one:
                updateWorkArea('1');
                break;
            case R.id.zero:
                updateWorkArea('0');
                break;
            case R.id.open_bracket:
                updateWorkArea('(');
                break;
            case R.id.close_bracket:
                updateWorkArea(')');
                break;
            case R.id.times:
                checkOperatorValidity('*');
                break;
            case R.id.division:
                checkOperatorValidity('/');
                break;
            case R.id.plus:
                checkOperatorValidity('+');
                break;
            case R.id.minus:
                checkOperatorValidity('-');
                break;
            case R.id.decimal:
                updateWorkArea('.');
                break;
            case R.id.clear:
                computationArea.setText("");
                break;

            case R.id.del:
                deleteFromWorkArea();
        }
    }

    public void updateWorkArea(char buttonText) {

        String currentExpression = computationArea.getText().toString();
        expressionValidator.setExpression(currentExpression);

        if(expressionValidator.validate(buttonText)) {
            currentExpression = currentExpression + buttonText;
            computationArea.setText(currentExpression);
        }
    }

    public void checkOperatorValidity(char buttonText) {
        String currentExpression = computationArea.getText().toString();
        expressionValidator.setExpression(currentExpression);
        computationArea.setText(expressionValidator.validateOperator(buttonText));
    }

    public void deleteFromWorkArea() {
        String currentExpression = computationArea.getText().toString();
        if(!currentExpression.isEmpty()) {
            currentExpression = currentExpression.substring(0,currentExpression.length()-1);
            computationArea.setText(currentExpression);
        }

    }


}
