package com.example.logisticare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logisticare.Entities.Parcel;

import java.util.ArrayList;
import java.util.List;


public class parcelsHistory extends AppCompatActivity  implements View.OnClickListener{

    public RecyclerView studentsRecycleView;
    private List<Parcel> students;





    public void Toasts(){
        Toast.makeText(getApplicationContext(),"rtg",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcels_history);

        studentsRecycleView = findViewById(R.id.studentsRecycleView);
        studentsRecycleView.setHasFixedSize(true);
        studentsRecycleView.setLayoutManager(new LinearLayoutManager(this));


        Firebase_DBManager.notifyToParcelList(new Firebase_DBManager.NotifyDataChange<List<Parcel>>() {
            @Override
            public void OnDataChanged(List<Parcel> obj) {
                if (studentsRecycleView.getAdapter() == null) {
                    students = obj;
                    studentsRecycleView.setAdapter(new StudentsRecycleViewAdapter(parcelsHistory.this, students));
                } else
                    studentsRecycleView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getBaseContext(), "error to get students list\n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Firebase_DBManager.stopNotifyToParcelList();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        v.setVisibility(View.GONE);
    }
}
