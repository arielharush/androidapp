package com.example.logisticare.Entities;
import android.location.Location;
import com.example.logisticare.Entities.Enums.*;
import com.google.firebase.database.Exclude;
import java.util.Calendar;
import java.util.Date;

public class Parcel {

    String key;
    PackType packType;
    Bool breakable;
    PackageWeight packageWeight;
    Location location;
    String receiver_phone;
    Date dateSend;
    PackStatus packStatus;
    String deliveryman_phone;
    Date dateReceived;


    public Parcel(String key, PackType packType, Bool breakable, PackageWeight packageWeight,
                  Location location, String receiver_phone, Date dateSend, PackStatus packStatus) {
        this.key = key;
        this.packType = packType;
        this.breakable = breakable;
        this.packageWeight = packageWeight;
        this.location = location;
        this.receiver_phone = receiver_phone;
        this.dateSend = new Date(dateSend.getTime());
        this.packStatus = packStatus;
        this.deliveryman_phone = null ;
        this.dateReceived = null ;
    }


    public Parcel(Parcel parcel){
        this.key = parcel.key;
        this.packType = parcel.packType;
        this.breakable = parcel.breakable;
        this.packageWeight = parcel.packageWeight;
        this.location =  new Location(parcel.location);
        this.receiver_phone = parcel.receiver_phone;
        this.deliveryman_phone = parcel.deliveryman_phone;
        this.dateSend = new Date(parcel.dateSend.getTime());
        this.dateReceived = new Date(parcel.dateReceived.getTime());
        this.packStatus =  parcel.packStatus;
}


    public Parcel() {
       this.key = null;
       this.packType = PackType.ENVELOPE;
       this.breakable = Bool.No;
       this.packageWeight = PackageWeight.UP_TO_500_GR;
       this.location = null;
       this.receiver_phone = null;
       this.deliveryman_phone = null;
       this.dateSend = new Date(Calendar.getInstance().getTime().getTime());
       this.dateReceived = null;
       this.packStatus = PackStatus.SENT;
    }

    public Parcel(Long lo) { }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PackType getPackType() {
        return packType;
    }

    public void setPackType(PackType packType) {
        this.packType = packType;
    }

    public Bool isBreakable() {
        return breakable;
    }

    public void setBreakable(Bool breakable) {
        this.breakable = breakable;
    }

    public PackageWeight getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(PackageWeight packageWeight) {
        this.packageWeight = packageWeight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getDeliveryman_phone() {
        return deliveryman_phone;
    }

    public void setDeliveryman_phone(String deliveryman_phone) {
        this.deliveryman_phone = deliveryman_phone;
    }

    public Date getDateSend() {
        return dateSend;
    }

    public void setDateSend(Date dateSend) {
        this.dateSend = dateSend;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public PackStatus getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(PackStatus packStatus) {
        this.packStatus = packStatus;
    }



    @Override
    public String toString() {
        return "Parcel{" +
                "key=" + key +
                ", packType=" + packType +
                ", breakable=" + breakable +
                ", packageWeight=" + packageWeight +
                ", location=" + location +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", deliveryman_phone='" + deliveryman_phone + '\'' +
                ", dateSend=" + dateSend +
                ", dateReceived=" + dateReceived +
                ", packStatus=" + packStatus +
                '}';
    }
}
