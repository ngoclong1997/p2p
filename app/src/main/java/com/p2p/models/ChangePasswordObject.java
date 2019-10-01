package com.p2p.models;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordObject {
    @SerializedName("current_password")
    String currentPassword;
    @SerializedName("new_password")
    String newPassword;

    public ChangePasswordObject() {

    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

