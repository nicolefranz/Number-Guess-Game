package com.finalexam;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public Intent intent;

    public EditText txtGuest;

    public Button BTNGuest;
    public Button BTNHistory;

    public TextView Number;
    public TextView Attempts;

    public int selectedNumber;
    public int attempts;
    
    private GameDatabase gamedatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuest = (EditText) findViewById(R.id.NumGuest);
        BTNGuest = (Button) findViewById(R.id.btnGuest);
        BTNHistory = (Button) findViewById(R.id.btnHistory);
        Number = (TextView) findViewById(R.id.DispNumber);
        Attempts = (TextView) findViewById(R.id.DispAttemps);
        gamedatabase = new GameDatabase(this);

        generateNumber();

        BTNGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guestString = txtGuest.getText().toString();

                if (guestString.isEmpty()) {
                    txtGuest.setError("Enter your guess!");
                    txtGuest.requestFocus();
                    return;
                }

                int guess = Integer.parseInt(guestString);

                attempts++;
                Attempts.setText(String.valueOf(attempts));
                String guestAttemp = Attempts.getText().toString();

                if (guess == selectedNumber) {
                    Toast.makeText(getApplicationContext(), "Congratulations! You guessed the correct number", Toast.LENGTH_SHORT).show();
                    Number.setText(String.valueOf(selectedNumber));

                    //attempts = 0;
                    //Attempts.setText(String.valueOf(attempts));
                    generateNumber();
                    
                    hideKeyboard(view);
                    
                    if(gamedatabase.AddRecords(guestString, guestAttemp)) {
                    	Toast.makeText(getApplicationContext(), "SAVED RESULT!", Toast.LENGTH_SHORT).show();
                    }else {
                    	Toast.makeText(getApplicationContext(), "SAVING RESULT FAILED!", Toast.LENGTH_SHORT).show();
                    }
                } else if (guess < selectedNumber) {
                    Toast.makeText(getApplicationContext(), "HIGHER!", Toast.LENGTH_SHORT).show();
                    txtGuest.setText(""); 
                } else {
                    Toast.makeText(getApplicationContext(), "LOWER!", Toast.LENGTH_SHORT).show();
                    txtGuest.setText(""); 
                }
            }
        });
        
        BTNHistory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				intent = new Intent(MainActivity.this, RecordsActivity.class);
				startActivity(intent);
				hideKeyboard(view);
				
				Attempts.setText("");
				txtGuest.setText("");
				Number.setText("");
			}
		});
    }

    public void generateNumber() {
        Random random = new Random();
        selectedNumber = random.nextInt(50) + 1;
    }
    
    private void hideKeyboard(View view) {
    	InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
