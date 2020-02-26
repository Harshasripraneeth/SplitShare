package com.pressure.splitshare;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pressure.splitshare.adapters.Adapter;
import com.pressure.splitshare.database.PersonDataBase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    ArrayList<Mate> person = new ArrayList<Mate>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    boolean x =true;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_listfrag, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Mate> list3 = loaddata();
        if(list3 !=null)
            person = list3;
        adapter = new Adapter(this.getActivity(), person);
        recyclerView.setAdapter(adapter);
    }

    void datachanged(String name,String tel,String amount) {
        int i = 0;
        try{

                person.add(new Mate(name, tel, Integer.parseInt(amount)));
                adapter.notifyDataSetChanged();

    }
        catch (Exception e)
        {
            Toast.makeText(this.getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
}
     ArrayList<Mate> loaddata()
     {

         PersonDataBase db2 = new PersonDataBase(this.getActivity());
         db2.open();
         ArrayList<Mate> list2 = db2.getdata();
         db2.close();
         return list2;
     }
     void deleteentry()
     {
         PersonDataBase db = new PersonDataBase(this.getActivity());
         db.open();
         person = db.getdata();
         adapter.notifyDataSetChanged();
         db.close();
     }
}
