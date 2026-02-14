package com.example.ParkEase20.entity;

public enum ImageDirectoryType {
    USER("user"),
    PROVIDER("provider"),
    APPLICATION("provider_application"),
    UPDATE_APPLICATION("update_application"),
    PARKING_AREA("parking_area");

    private String folderName;
    ImageDirectoryType(String folderName) {
        this.folderName = folderName;
    }
    public String getFolderName() {
        return folderName;
    }
}
