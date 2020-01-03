package com.example.logisticare;


import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logisticare.Entities.Enums.PackStatus;
import com.example.logisticare.Entities.Parcel;
import com.google.android.gms.common.internal.Objects;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


import static android.view.View.VISIBLE;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.PermissionChecker.checkSelfPermission;


/**
 * Created by Yair on 08/12/2019.
 */

public class StudentsRecycleViewAdapter extends RecyclerView.Adapter<StudentsRecycleViewAdapter.StudentViewHolder> {

    private int index_t;
    private Context baseContext;
    List<Parcel> students;
private static   int time =0;
    public StudentsRecycleViewAdapter(Context baseContext, List<Parcel> students) {
        this.students = students;
        this.baseContext = baseContext;
    }

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }


    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(baseContext.getApplicationContext()).inflate(R.layout.parcel_item,
                parent,
                false);

        return new StudentViewHolder(v);
    }

    @Override
    public void onViewRecycled(@NonNull StudentViewHolder holder) {
        super.onViewRecycled(holder);
        holder.linearLayout.setVisibility(View.GONE);
        holder.dphoneLinner.setVisibility(View.GONE);
        holder.linearLayoutdate.setVisibility(View.GONE);
       // Toast.makeText(baseContext.getApplicationContext(),"f",Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        long time= System.currentTimeMillis();
        Parcel student = students.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(student.getDateSend());
        String date = helperDateAndHour(calendar.get(calendar.DAY_OF_MONTH))  + "/" + helperDateAndHour(calendar.get(Calendar.MONTH) +1 ) + "/" + calendar.get(Calendar.YEAR);
        String hour = helperDateAndHour(calendar.get(Calendar.HOUR))  + ":" + helperDateAndHour(calendar.get(Calendar.MINUTE));
        holder.dateSend.setText(date);
        holder.packType.setText(Parcel.PackTypeTosString(student.getPackType()));
        holder.packageWeight.setText(Parcel.packageWeightTosString(student.getPackageWeight()));
        holder.hourSend.setText(hour);
        holder.stateParcel.setText(Parcel.packStatusTosString(student.getPackStatus()));
        String uri = "@drawable/myresource";  // where myresource (without the extension) is the file
        holder.phoner.setText(student.getReceiver_phone());
        holder.phoned.setText(student.getDeliveryman_phone());
        PackStatus status = holder.packStatus;

        if(student.getDateReceived() != null){

            calendar = Calendar.getInstance();
            calendar.setTime(student.getDateReceived());
            date = helperDateAndHour(calendar.get(calendar.DAY_OF_MONTH))  + "/" + helperDateAndHour(calendar.get(Calendar.MONTH) +1 ) + "/" + calendar.get(Calendar.YEAR);
            hour = helperDateAndHour(calendar.get(Calendar.HOUR))  + ":" + helperDateAndHour(calendar.get(Calendar.MINUTE));
            holder.dateReceived.setText(date);
            holder.hourReceived.setText(hour);
        }



       // Toast.makeText(baseContext.getApplicationContext(),time + "",Toast.LENGTH_SHORT).show();
        if (student.isBreakable() == true) {

            holder.Breakable_parcel.setText("Yes.");
        } else {
            holder.Breakable_parcel.setText("No.");
        }
//holder.Breakable_parcel.setText("");
        if (student.getPackStatus() == PackStatus.SENT) {
            holder.imageViewStateParcel.setImageResource(R.drawable.sent_image);
        }
        if (student.getPackStatus() == PackStatus.OFFER_FOR_SHIPPING) {
            holder.imageViewStateParcel.setImageResource(R.drawable.offer_for_shipping);
        }
        if (student.getPackStatus() == PackStatus.IN_THE_WHY) {
            holder.imageViewStateParcel.setImageResource(R.drawable.in_the_why);
            holder.dphoneLinner.setVisibility(VISIBLE);
        }
        if (student.getPackStatus() == PackStatus.RECEIVED) {
            holder.imageViewStateParcel.setImageResource(R.drawable.received);
            holder.dphoneLinner.setVisibility(VISIBLE);
            holder.linearLayoutdate.setVisibility(VISIBLE);

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

    public String helperDateAndHour(int d){

        if(d < 10){
            return "0" + d;
        }else {
            return  d + "";
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    class StudentViewHolder extends RecyclerView.ViewHolder{

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
        TextView dateReceived;
        TextView hourReceived;
        TextView Breakable_parcel;
        final TextView phoner;
        final LinearLayout linearLayoutdate;
        final LinearLayout linearLayout;
        final LinearLayout dphoneLinner;
        TextView phoned;

        StudentViewHolder(final View itemView) {
            super(itemView);
            //    personImageView = itemView.findViewById(R.id.personImageView);
            //  nameTextView = itemView.findViewById(R.id.nameTextView);
            //  phoneTextView = itemView.findViewById(R.id.phoneTextView);
            dateReceived = itemView.findViewById(R.id.dateReceived);
            hourReceived = itemView.findViewById(R.id.hourReceived);
            linearLayoutdate = itemView.findViewById(R.id.linnerDateReceived);
            dateSend = itemView.findViewById(R.id.ParcelDateSend);
            packType = itemView.findViewById(R.id.ParcelType);
            packageWeight = itemView.findViewById(R.id.ParcelWeight);
            hourSend = itemView.findViewById(R.id.ParcelDateSendTime);
            imageViewStateParcel = itemView.findViewById(R.id.imageStateParcel);
            stateParcel = itemView.findViewById(R.id.StateParcel);
            Breakable_parcel = itemView.findViewById(R.id.breakable_parcel);
            linearLayout = itemView.findViewById(R.id.LinnerParcel);
            phoner = itemView.findViewById(R.id.phoner);
            phoned = itemView.findViewById(R.id.phoned);
            dphoneLinner = itemView.findViewById(R.id.dphone);
            itemView.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(true){
                       linearLayout.setVisibility(View.GONE);
                        time++;

                    }

                }
            });

            itemView.findViewById(R.id.shareButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    String text = "Parcel info:";
                    text =  text + "\n \n";
                    text =  text + "\n";
                    Parcel parcel = students.get(getAdapterPosition());
                    text = text + "Parcel type: " +Parcel.PackTypeTosString(parcel.getPackType()) + ".";
                    text =  text + "\n";
                    text =  text + "Parcel status: "+ Parcel.packStatusTosString(parcel.getPackStatus())+ ".";
                    text =  text + "\n";
                    text =  text + "Parcel Weight: " + Parcel.packageWeightTosString(parcel.getPackageWeight());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(parcel.getDateSend());
                    String date = helperDateAndHour(calendar.get(calendar.DAY_OF_MONTH))  + "/" + helperDateAndHour(calendar.get(Calendar.MONTH) +1 ) + "/" + calendar.get(Calendar.YEAR);
                    String hour = helperDateAndHour(calendar.get(Calendar.HOUR))  + ":" + helperDateAndHour(calendar.get(Calendar.MINUTE));
                    text =  text + "\n";
                    text =  text + "Shipping date: " + date + "      " + hour;
                    text =  text + "\n";
                    text =  text + "Receiver phone: " + parcel.getReceiver_phone();
                    text =  text + "\n";
                    if (parcel.getPackStatus() == PackStatus.IN_THE_WHY ||parcel.getPackStatus() == PackStatus.RECEIVED) {
                        text =  text + "Deliveryman phone: " + parcel.getDeliveryman_phone();
                        text =  text + "\n";

                    }
                    if(parcel.getPackStatus() == PackStatus.RECEIVED && parcel.getDateReceived() != null){


                        calendar.setTime(parcel.getDateReceived());
                        date = helperDateAndHour(calendar.get(calendar.DAY_OF_MONTH))  + "/" + helperDateAndHour(calendar.get(Calendar.MONTH) +1 ) + "/" + calendar.get(Calendar.YEAR);
                        hour = helperDateAndHour(calendar.get(Calendar.HOUR))  + ":" + helperDateAndHour(calendar.get(Calendar.MINUTE));

                        text =  text + "Date Received: " + date + "      " + hour;
                        text =  text + "\n";
                    }


                    sendIntent.putExtra(Intent.EXTRA_TEXT, text);

                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    baseContext.startActivity(shareIntent);
                }
            });
            itemView.findViewById(R.id.callrphone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(baseContext.getApplicationContext(),"Receiver phone copy to \"Clip Board\"",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoner.getText().toString()));
                    baseContext.startActivity(intent);
                }
            });

            itemView.findViewById(R.id.calldphone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoned.getText().toString()));



                    baseContext.startActivity(intent);
        //     Check the SDK version and whether the permission is already granted or not.






    }
});
itemView.findViewById(R.id.copyrphone).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        setClipboard(baseContext, phoner.getText().toString());
        Toast.makeText(baseContext.getApplicationContext(),"Receiver phone copy to \"Clip Board\"",Toast.LENGTH_SHORT).show();
    }
});

            itemView.findViewById(R.id.copydphone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClipboard(baseContext, phoned.getText().toString());
                    Toast.makeText(baseContext.getApplicationContext(),"Deliveryman phone copy to \"Clip Board\"",Toast.LENGTH_SHORT).show();
                }
            });



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  int position = getAdapterPosition();
                  //  String id = students.get(position).getKey();
                    LinearLayout linearLayout;
                    if(v.findViewById(R.id.LinnerParcel).getVisibility() == VISIBLE)
                    {


                    }else{
                        linearLayout =  (LinearLayout)v.findViewById(R.id.LinnerParcel);
                        linearLayout.setVisibility(VISIBLE);
                    //    Toast.makeText(baseContext.getApplicationContext(),"Receiver phone copy to \"Clip Board\"",Toast.LENGTH_SHORT).show();

                    }

                }
            });


            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Select Action");

                    MenuItem delete = menu.add(Menu.NONE, 1, 1, "Close");

                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                       //     int position = getAdapterPosition();
                         ////   String id = students.get(position).getKey();
                    v.findViewById(R.id.LinnerParcel).setVisibility(View.GONE);
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