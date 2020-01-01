package com.example.logisticare;


import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.logisticare.Entities.Parcel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class Firebase_DBManager {


    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    static DatabaseReference ParcelsRef;
    static List<Parcel> ParcelList;

    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/");
        ParcelsRef = database.getReference("parcels");
        ParcelList = new ArrayList<>();
    }


    public static void addParcel(final Parcel Parcel, final Action<Long> action) {
//        if (Parcel.getImageLocalUri() != null) {
//            // upload image
//          //  StorageReference imagesRef = FirebaseStorage.getInstance().getReference();
//          //  imagesRef = imagesRef.child("images").child(System.currentTimeMillis() + ".jpg");
//
//            imagesRef.putFile(Parcel.getImageLocalUri())
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            action.onProgress("upload Parcel data", 90);
//                            // Get a URL to the uploaded content
//                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            Parcel.setImageFirebaseUrl(downloadUrl.toString());
//
//                            // add Parcel
//                            addParcelToFirebase(Parcel, action);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            action.onFailur;11e(exception);
//                            action.onProgress("error upload Parcel image", 100);
//                        }
//                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double uploadBytes = taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
//                    double progress = (90.0 * uploadBytes);
//                    action.onProgress("upload image", progress);
//                }
//            });
//        } else
//            action.onFailure(new Exception("select image first ..."));
    }

    private static void addParcelToFirebase(final Parcel Parcel, final Action<Long> action) {
        String key = Parcel.getKey().toString();
        ParcelsRef.child(key).setValue(Parcel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // action.onSuccess((Long)Parcel.getKey());
                action.onProgress("upload Parcel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload Parcel data", 100);

            }
        });
    }


    public static void removeParcel(String id, final Action<Long> action) {

//        final String key = ( id).toString();
//
//        ParcelsRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                final Parcel value = dataSnapshot.getValue(Parcel.class);
//                if (value == null)
//                    action.onFailure(new Exception("Parcel not find ..."));
//                else {
//               //     StorageReference imagesRef = FirebaseStorage.getInstance().getReferenceFromUrl(value.getImageFirebaseUrl());
//                    imagesRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            ParcelsRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                  //  action.onSuccess(value.getKey());
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    action.onFailure(e);
//                                }
//                            });
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            action.onFailure(e);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                action.onFailure(databaseError.toException());
//            }
//        });
    }

    public static void updateParcel(final Parcel toUpdate, final Action<Long> action) {
        final String key = ( toUpdate.getKey()).toString();

        removeParcel(toUpdate.getKey(), new Action<Long>() {
            @Override
            public void onSuccess(Long obj) {
                addParcel(toUpdate, action);
            }

            @Override
            public void onFailure(Exception exception) {
                action.onFailure(exception);
            }

            @Override
            public void onProgress(String status, double percent) {
                action.onProgress(status, percent);
            }
        });
    }

    private static ChildEventListener ParcelRefChildEventListener;

    public static void notifyToParcelList(final NotifyDataChange<List<Parcel>> notifyDataChange) {
        if (notifyDataChange != null) {

            if (ParcelRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify Parcel list"));
                return;
            }
            ParcelList.clear();

            ParcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Parcel Parcel = dataSnapshot.getValue(Parcel.class);
                    String id = dataSnapshot.getKey();
                    Parcel.setKey((id));
                    ParcelList.add(Parcel);


                    notifyDataChange.OnDataChanged(ParcelList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Parcel Parcel = dataSnapshot.getValue(Parcel.class);
                    String id = (dataSnapshot.getKey());
                    Parcel.setKey(id);


                    for (int i = 0; i < ParcelList.size(); i++) {
                        if (ParcelList.get(i).getKey().equals(id)) {
                            ParcelList.set(i, Parcel);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(ParcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Parcel Parcel = dataSnapshot.getValue(Parcel.class);
                    String id = (dataSnapshot.getKey());
                    Parcel.setKey(id);

                    for (int i = 0; i < ParcelList.size(); i++) {
                        if (ParcelList.get(i).getKey() == id) {
                            ParcelList.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(ParcelList);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            ParcelsRef.addChildEventListener(ParcelRefChildEventListener);
        }
    }

    public static void stopNotifyToParcelList() {
        if (ParcelRefChildEventListener != null) {
            ParcelsRef.removeEventListener(ParcelRefChildEventListener);
            ParcelRefChildEventListener = null;
        }
    }

}
