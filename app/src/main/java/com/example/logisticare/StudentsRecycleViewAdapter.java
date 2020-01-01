package com.example.logisticare;


import android.content.Context;

import android.location.Location;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticare.Entities.Enums.PackStatus;
import com.example.logisticare.Entities.Parcel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Yair on 08/12/2019.
 */

public class StudentsRecycleViewAdapter extends RecyclerView.Adapter<StudentsRecycleViewAdapter.StudentViewHolder> {

    private Context baseContext;
    List<Parcel> students;

    public StudentsRecycleViewAdapter(Context baseContext, List<Parcel> students) {
        this.students = students;
        this.baseContext = baseContext;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(baseContext.getApplicationContext() ).inflate(R.layout.parcel_item,
                parent,
                false);

        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {

        Parcel student = students.get(position);
        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(student.getDateSend());
       String date = calendar.get(calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
      String hour = calendar.get(Calendar.HOUR) + ":"+calendar.get(Calendar.MINUTE);
       holder.dateSend.setText(date);
       holder.packType.setText(Parcel.PackTypeTosString(student.getPackType()));
       holder.packageWeight.setText(Parcel.packageWeightTosString(student.getPackageWeight()));
       holder.hourSend.setText(hour);
       holder.stateParcel.setText(Parcel.packStatusTosString(student.getPackStatus()));
        String uri = "@drawable/myresource";  // where myresource (without the extension) is the file

PackStatus status = holder.packStatus;

if (student.getPackStatus() == PackStatus.SENT){
    holder.imageViewStateParcel.setImageResource(R.drawable.sent_image);
}
        if (student.getPackStatus() == PackStatus.OFFER_FOR_SHIPPING){
            holder.imageViewStateParcel.setImageResource(R.drawable.offer_for_shipping);
        }
        if (student.getPackStatus() == PackStatus.IN_THE_WHY){
            holder.imageViewStateParcel.setImageResource(R.drawable.in_the_why);
        }
        if (student.getPackStatus() == PackStatus.RECEIVED){
            holder.imageViewStateParcel.setImageResource(R.drawable.received);
        }
//set the image to the imageView

      //  holder.phoneTextView.setText(student.toString());

        //Load the image using Glide
//        Glide.with(baseContext.getApplicationContext())
//                .load(student.getImageFirebaseUrl())
//                .centerCrop()
//                .override(150, 150)
//                .placeholder(R.mipmap.person)
//                .into(holder.personImageView);


        //            Glide.with(getBaseContext() /* context */)
        //                    .load(student.getImageFirebaseUrl())
        //                 //   .override(450,450)
        //                    .centerCrop()
        //                    .placeholder(R.mipmap.person)
        //                    .into(holder.personImageView);


    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView dateSend;
        TextView packType;
        TextView hourSend;
        TextView breakable;
        TextView packageWeight;
        ImageView imageViewStateParcel;
        TextView stateParcel;
        Location location;
        String receiver_phone;
       // Date dateSend;
        PackStatus packStatus;
        String deliveryman_phone;
        Date dateReceived;
        StudentViewHolder(View itemView) {
            super(itemView);
        //    personImageView = itemView.findViewById(R.id.personImageView);
          //  nameTextView = itemView.findViewById(R.id.nameTextView);
          //  phoneTextView = itemView.findViewById(R.id.phoneTextView);
            dateSend = itemView.findViewById(R.id.ParcelDateSend);
            packType = itemView.findViewById(R.id.ParcelType);
            packageWeight = itemView.findViewById(R.id.ParcelWeight);
            hourSend = itemView.findViewById(R.id.ParcelDateSendTime);
            imageViewStateParcel = itemView.findViewById(R.id.imageStateParcel);
            stateParcel = itemView.findViewById(R.id.StateParcel);

            // itemView.setOnClickListener();
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Select Action");

                    MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int position = getAdapterPosition();
                            String id = students.get(position).getKey();

//                            Firebase_DBManager.removeStudent(id, new Firebase_DBManager.Action<Long>() {
//                                @Override
//                                public void onSuccess(Long obj) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Exception exception) {
//
//                                }
//
//                                @Override
//                                public void onProgress(String status, double percent) {
//
//                                }
//                            });

                            return true;
                        }
                    });
                }
            });
        }
    }
}