package com.pressure.splitshare;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pressure.splitshare.database.AmountDB;

public class HistoryActivity extends AppCompatActivity {
    TextView tvdata;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
         tvdata = (TextView) findViewById(R.id.tvdata);
             name = getIntent().getStringExtra("NAME");
             load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id)
        {
            case R.id.deleteHistory : deleteHistory();
                                      break;
        }
        return super.onOptionsItemSelected(item);
    }

    void load()
    {
        AmountDB db = new AmountDB(this,name);
        db.open();
        String string = db.getdata();
        if(string != null)
        tvdata.setText(string);
        else
            tvdata.setText("no transaction");
        db.close();
    }

    void deleteHistory()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setTitle("Attention")
                .setMessage("Delete total transactions if there is any pending amount please save the final amount")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AmountDB db = new AmountDB(HistoryActivity.this,name);
                        db.open();
                        db.deleteentry(name);
                        db.close();
                        load();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
