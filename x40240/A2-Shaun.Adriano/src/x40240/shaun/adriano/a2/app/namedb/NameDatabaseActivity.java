package x40240.shaun.adriano.a2.app.namedb;

import org.apache.http.protocol.HTTP;

import x40240.shaun.adriano.a2.app.model.PersonInfo;
import x40240.shaun.adriano.a2.app.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.CheckBox;

@SuppressWarnings ("unused")
public class NameDatabaseActivity
    extends Activity
{
    private static final String LOGTAG = NameDatabaseActivity.class.getSimpleName();
    //public static final String CUSTOM_INTENT = "x40240.shaun.adriano.a2.app.custom.intent.action";
    
    private Button       okButton;
    private Button       clearButton;
    private EditText     firstnameText;
    private EditText     lastnameText;
    private Spinner      genderSpinner;
    private CheckBox	 breakfastBox;
    private CheckBox	 lunchBox;
    private CheckBox	 dinnerBox;
    
    private Context context = this;  // our execution context
    
    /*
    public class CustomOnItemSelectedListener implements OnItemSelectedListener {
    	 
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                long id) {
             
           parent.getItemAtPosition(pos);
        }
     
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
     
        }
    }
    
    public void addListenerOnSpinnerItemSelection(){
        
        genderSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
    */
    
    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //  Not used, but demonstrates how we can track clicks on the EditText field
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.d(LOGTAG, "firstnameText: Got Click!");
            }
        };
        
        //  Demonstrates how we can monitor/respond to each key typed into the EditText field
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey (View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Log.d(LOGTAG, view.getClass().getSimpleName()+": Key event!");
                            return true;
                    }
                }
                return false;
            }
        };
        

        
        firstnameText = (EditText) findViewById(R.id.firstname_text);
        firstnameText.setOnClickListener(onClickListener);
        firstnameText.setOnKeyListener(onKeyListener);
        
        lastnameText = (EditText) findViewById(R.id.lastname_text);
        lastnameText.setOnClickListener(onClickListener);
        lastnameText.setOnKeyListener(onKeyListener);
        
       
        breakfastBox = (CheckBox) this.findViewById(R.id.breakfast_label);
        lunchBox = (CheckBox) this.findViewById(R.id.lunch_label);
        dinnerBox = (CheckBox) this.findViewById(R.id.dinner_label);
        
        genderSpinner = (Spinner) this.findViewById(R.id.genderSpinner);
        
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        //addListenerOnSpinnerItemSelection();
        

        
        
        okButton = (Button) this.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                PersonInfo personInfo = new PersonInfo();
                personInfo.setFirstname(firstnameText.getText().toString());
                personInfo.setLastname(lastnameText.getText().toString());
                
                if (genderSpinner.getSelectedItem() == "Select Gender") personInfo.setGender(PersonInfo.GENDER_UNKNOWN); 
                if (genderSpinner.getSelectedItem() == "Male") personInfo.setGender(PersonInfo.GENDER_MALE);
                if (genderSpinner.getSelectedItem() == "Female") personInfo.setGender(PersonInfo.GENDER_FEMALE);
                
                
                
                if (breakfastBox.isChecked())  personInfo.setFavmeal("Breakfast"); 
                else if (lunchBox.isChecked()) personInfo.setFavmeal("Lunch");
                else if (dinnerBox.isChecked()) personInfo.setFavmeal("Dinner");
                else if (breakfastBox.isChecked() && lunchBox.isChecked()) personInfo.setFavmeal("Breakfast, Lunch");
                else if (breakfastBox.isChecked() && dinnerBox.isChecked()) personInfo.setFavmeal("Breakfast, Dinner");
                else if (dinnerBox.isChecked() && lunchBox.isChecked()) personInfo.setFavmeal("Lunch, Dinner");
                else if (dinnerBox.isChecked() && lunchBox.isChecked() && breakfastBox.isChecked()) personInfo.setFavmeal("Breakfast, Lunch, Dinner");
                
                
                Log.d(LOGTAG, "firstname="+personInfo.getFirstname());
                Log.d(LOGTAG, "lastname="+personInfo.getLastname());
                Log.d(LOGTAG, "gender="+personInfo.getGender());
                Log.d(LOGTAG, "favmeal="+personInfo.getFavmeal());
                
                // Implicit Intent
                Intent myIntent = new Intent();
                myIntent.setAction(Intent.ACTION_SEND);
                myIntent.putExtra("personInfo", personInfo);
                myIntent.setType(HTTP.PLAIN_TEXT_TYPE);
                
                

                startActivity(myIntent);
            }
        });
        clearButton = (Button) this.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstnameText.setText(null);
                lastnameText.setText(null);
                
            }
        });
    }
}