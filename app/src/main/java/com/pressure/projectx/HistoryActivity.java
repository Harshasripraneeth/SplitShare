package com.pressure.projectx;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.pressure.projectx.database.AmountDB;

public class HistoryActivity extends AppCompatActivity {
    TextView tvdata;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
         tvdata = (TextView) findViewById(R.id.tvdata);
         try
         {
             name = getIntent().getStringExtra("NAME");
            AmountDB db = new AmountDB(this,name);
            db.open();
            tvdata.setText(db.getdata());
            db.close();
         }
         catch (SQLException e)
         {
             Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
         }
    }
}
