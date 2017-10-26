package com.sby.risk.risksdk.model.header;

/**
 * Created by sby on 2017/8/10.
 */
public class HeaderInfo {

    private AppInfo appinfo;

    private DeviceInfo deviceinfo;

    private NetworkInfo networkinfo;

    public HeaderInfo() {
    }

    public HeaderInfo(AppInfo appinfo, DeviceInfo deviceinfo, NetworkInfo networkinfo) {
        this.appinfo = appinfo;
        this.deviceinfo = deviceinfo;
        this.networkinfo = networkinfo;
    }

    public AppInfo getAppinfo() {
        return appinfo;
    }

    public void setAppinfo(AppInfo appinfo) {
        this.appinfo = appinfo;
    }

    public DeviceInfo getDeviceinfo() {
        return deviceinfo;
    }

    public void setDeviceinfo(DeviceInfo deviceinfo) {
        this.deviceinfo = deviceinfo;
    }

    public NetworkInfo getNetworkinfo() {
        return networkinfo;
    }

    public void setNetworkinfo(NetworkInfo networkinfo) {
        this.networkinfo = networkinfo;
    }
}
