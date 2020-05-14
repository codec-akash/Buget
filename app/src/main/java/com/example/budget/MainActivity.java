package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText reason;
    EditText value;
    EditText amount;

    TextView amount_left;
    TextView total_money;

    DatabaseHelper databaseHelper;
    ArrayList<objects> arrayList;
    customAdapter adapter;

    SharedPreferences sharedPreferences;

    int total = 0;
    int amount_left_value = 0;
    int amount_value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences =this.getSharedPreferences("com.example.budget", Context.MODE_PRIVATE);

        listView = findViewById(R.id.list_item);

        reason = (EditText) findViewById(R.id.reason);
        value = (EditText) findViewById(R.id.rupees);
        amount = (EditText) findViewById(R.id.total_money);

        amount_left = (TextView)findViewById(R.id.amount_left);
        total_money = (TextView) findViewById(R.id.total_rupees);

        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();

        loadData();

        int a = sharedPreferences.getInt("Money",0);
        amount.setText(""+a);

        amount_left.setText(""+(a-total));


    }

    public void update(View view){

        String reason_txt = reason.getText().toString();

        if(reason_txt.equals("")){
            setAmount();
        } else {

            int value_txt = Integer.parseInt(value.getText().toString());

            if (reason_txt.equals("") || value.getText().toString().equals("")) {
                Toast.makeText(this, "make a valid Entries", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, reason_txt + " " + value_txt, Toast.LENGTH_SHORT).show();

                boolean result = databaseHelper.insertData(reason_txt, value_txt);

                if (result == true) {
                    Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "FAil", Toast.LENGTH_LONG).show();
                }

                reason.setText(null);
                value.setText(null);
                loadData();
                setAmount();
            }
        }
    }

    private void loadData() {
        arrayList = databaseHelper.getAllData();
        adapter = new customAdapter(this,arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        total = databaseHelper.getSum();
        total_money.setText(""+ total);
        Log.i("Total",String.valueOf(total));
    }

    public void setAmount(){
        amount_value = Integer.parseInt(amount.getText().toString());

        if (amount.getText().toString().equals("")){
            Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
        } else {
            amount_value = Integer.parseInt(amount.getText().toString());
            amount_left_value = amount_value - total;

            sharedPreferences.edit().putInt("Money",amount_value).apply();
            int a = sharedPreferences.getInt("Money",0);
            amount.setText(""+a);

            if (amount_left_value <= 0){
                Toast.makeText(this, "All money has been used", Toast.LENGTH_SHORT).show();
                amount_left.setText(""+amount_left_value);
            } else {
                amount_left.setText(""+amount_left_value);
            }
        }

    }

 /*   public void display(View view){
        Cursor cursor = databaseHelper.getAllDAta();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("name " + cursor.getString(1) + "\n");
                stringBuffer.append("age" + cursor.getString(2)+"\n");
            }

            Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
        }
    }

  */


}
