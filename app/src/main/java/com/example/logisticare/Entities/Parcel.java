package com.example.logisticare.Entities;


import android.location.Location;

import java.util.Calendar;
import java.util.Date;

public class Parcel {

    public static enum PackType{ENVELOPE,SMALL_PACK,BIG_PACK}
    public  static enum PackageWeight{UP_TO_500_GR,UP_TO_1_KG,UP_TO_5_KG,UP_TO_20_KG}
    public static enum  PackStatus{SENT,OFFER_FOR_SHIPPING,IN_THE_WHY,RECEIVED}

    private int id;
    PackType packType;
    boolean breakable;
    PackageWeight packageWeight;
    Location location;
    String receiver_phone;
    Date dateSend;
    PackStatus packStatus;
    String deliveryman_phone;
    Date dateReceived;

    public Parcel(int id, PackType packType, boolean breakable, PackageWeight packageWeight, Location location, String receiver, Date dateSend, PackStatus packStatus) {
        this.id = id;
        this.packType = packType;
        this.breakable = breakable;
        this.packageWeight = packageWeight;
        this.location = new Location(location);
        this.receiver_phone = receiver;
        this.dateSend = new Date(dateSend.getTime());
        this.packStatus = packStatus;
    }


    public Parcel(Parcel parcel){

        this.id = parcel.id;
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
       this.id = 0;
       this.packType = PackType.ENVELOPE;
       this.breakable = false;
       this.packageWeight = PackageWeight.UP_TO_500_GR;
       this.location = new Location("A");
       this.receiver_phone = "";
       this.deliveryman_phone = "";
       this.dateSend = new Date(Calendar.getInstance().getTime().getTime());
       this.dateReceived = null;
       this.packStatus = PackStatus.SENT;
    }

    public Parcel(Long lo) {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PackType getPackType() {
        return packType;
    }

    public void setPackType(PackType packType) {
        this.packType = packType;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
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
                "id=" + id +
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
