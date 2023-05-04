package com.asparmar6262.doctorplus;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Doctor implements Parcelable {
    private String name,specialties,email,password,mobileNo,image,role;

    public Doctor(String name, String specialties, String email, String password, String mobileNo, String image, String role) {
        this.name = name;
        this.specialties = specialties;
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
        this.image = image;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    protected Doctor(Parcel in){
        this.name = in.readString();
        this.specialties = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.mobileNo = in.readString();
        this.image = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel parcel) {
            return new Doctor(parcel);
        }

        @Override
        public Doctor[] newArray(int i) {
            return new Doctor[i];
        }
    };

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(specialties);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(mobileNo);
        parcel.writeString(image);
    }
}
