package com.sby.risk.risksdk.model.header;

/**
 * Created by sby on 2017/8/10.
 */
public class NetworkInfo {

    //operator
    private String carrier;
    //is wifi
    private Boolean wifi_ind;
    //IP
    private String ip_addr;
    //latitude
    private String latitude;
    //longitude
    private String longitude;


    public NetworkInfo() {
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public Boolean getWifi_ind() {
        return wifi_ind;
    }

    public void setWifi_ind(Boolean wifi_ind) {
        this.wifi_ind = wifi_ind;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


}
