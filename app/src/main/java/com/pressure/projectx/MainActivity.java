package com.pressure.projectx;
import android.content.Intent;
import android.database.SQLException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pressure.projectx.adapters.Adapter;
import com.pressure.projectx.database.AmountDB;
import com.pressure.projectx.database.PersonDataBase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.itemclicked {
    String iname="";
    Button btninfo,btndelete;
    EditText etamount, etpurpose, etrname, etrtel, etramount;
    Button btndetail, btnedit, btnsubmit, btnshow, btnadd, btnadd2;
    ImageView ivphoto;
    TextView tvname, tvtel, tvamount, textView, textView2;
    FragmentManager fragmentManager;
    Fragment buttonfrag, editfrag, historyfrag, listfrag, ownerfrag, addfrag,listfrag1;

     int reamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btninfo = findViewById(R.id.btninfo);
        btndetail = findViewById(R.id.btndetail);
        ivphoto = findViewById(R.id.ivphoto);
        tvname = findViewById(R.id.tvname);
        tvtel = findViewById(R.id.tvtel);
        etamount = findViewById(R.id.etamount);
        etpurpose = findViewById(R.id.etpurpose);
        btnadd = findViewById(R.id.btnadd);
        btnadd2 = findViewById(R.id.btnadd2);
        btndelete = findViewById(R.id.btndelete);
        etrname = findViewById(R.id.etrname);
        etrtel = findViewById(R.id.etrtel);
        etramount = findViewById(R.id.etramount);
        fragmentManager = getSupportFragmentManager();
        addfrag = fragmentManager.findFragmentById(R.id.addfrag);
        buttonfrag = fragmentManager.findFragmentById(R.id.buttonfrag);
        listfrag = fragmentManager.findFragmentById(R.id.listfrag);
        listfrag1 = (ListFragment) fragmentManager.findFragmentById(R.id.listfrag);

        historyfrag = fragmentManager.findFragmentById(R.id.historyfrag);
        ownerfrag = fragmentManager.findFragmentById(R.id.ownerfrag);
        editfrag = fragmentManager.findFragmentById(R.id.editfrag);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnshow = findViewById(R.id.btnshow);
        btnedit = findViewById(R.id.btnedit);
        textView2 = findViewById(R.id.textView2);
        fragmentManager.beginTransaction()
                .show(buttonfrag)
                .show(listfrag)
                .show(ownerfrag)
                .hide(historyfrag)
                .hide(editfrag)
                .hide(addfrag)
                .commit();
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .show(ownerfrag)
                        .hide(historyfrag)
                        .hide(editfrag)
                        .hide(addfrag)
                        .commit();
            }
        });
        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .show(historyfrag)
                        .hide(ownerfrag)
                        .hide(editfrag)
                        .hide(addfrag)
                        .commit();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDataBase db = new PersonDataBase(MainActivity.this);
                db.open();
                db.deleteentry(iname);
                db.close();

                AmountDB db1= new AmountDB(MainActivity.this,iname);
                db1.open();
                db1.deleteentry();
                db1.close();
                ((ListFragment) listfrag1).deleteentry();
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .show(editfrag)
                        .hide(ownerfrag)
                        .hide(historyfrag)
                        .hide(addfrag)
                        .commit();

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .hide(historyfrag)
                        .hide(ownerfrag)
                        .hide(editfrag)
                        .show(addfrag)
                        .commit();

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purpose = etpurpose.getText().toString().trim();
                String amount = etamount.getText().toString().trim();
                try {
                    AmountDB db = new AmountDB(MainActivity.this,iname);
                    db.open();
                    db.createEntry(purpose, amount);
                    db.close();
                    Toast.makeText(MainActivity.this, "Succesfully submitted", Toast.LENGTH_SHORT).show();
                    etpurpose.setText("");
                    etamount.setText("");
                    display();
                } catch (SQLException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtra("NAME",iname);
                startActivity(intent);
            }
        });
        btnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PersonDataBase db2 = new PersonDataBase(MainActivity.this);
                   String name = etrname.getText().toString();
                   String tel = etrtel.getText().toString().trim();
                   String amount = etramount.getText().toString();
                   db2.open();
                   db2.createEntry(name,tel,amount);
                    ((ListFragment) listfrag1).datachanged(name,tel,amount);
                    db2.close();
                    AmountDB db = new AmountDB(MainActivity.this,name);
                    db.open();
                    db.createEntry("inital amount",amount);
                    db.close();
                    etrname.setText("");
                    etrtel.setText("");
                    etramount.setText("");
                 }
                catch (SQLException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                catch (NullPointerException e)
                {
                    Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onitemclicked(int index) {
        ArrayList<Mate> list5 = ((ListFragment) listfrag1).loaddata();
        textView2.setText("");
        AmountDB db = new AmountDB(MainActivity.this, iname);
        db.open();
        reamount = db.getamount();
        db.close();
        iname = list5.get(index).getName();
        tvname.setText(list5.get(index).getName());
        tvtel.setText(list5.get(index).getTel());
           display();
        if (list5.get(index).getName().equals("bhargav")) {
            ivphoto.setImageResource(R.drawable.bhargav);
        } else if (list5.get(index).getName().equals("bhash")) {
            ivphoto.setImageResource(R.drawable.bhash);
        } else if (list5.get(index).getName().equals("praneeth")) {
            ivphoto.setImageResource(R.drawable.praneeth);
        }
    }
    void display()
    {
        AmountDB db = new AmountDB(MainActivity.this, iname);
        db.open();
        reamount = db.getamount();
        db.close();
        if (reamount > 0) {
            textView2.setText("the amount should be received: " + reamount);
            reamount =0;
        }
        else{
            textView2.setText("the amount should be given: " + reamount);
            reamount =0;}

    }
}