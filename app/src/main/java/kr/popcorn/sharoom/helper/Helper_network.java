package kr.popcorn.sharoom.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Helper_network {
    private Activity activity;

    public Helper_network() {
        super();
    }
    public Helper_network(Activity activity) {
        this.activity = activity;
    }

    public Boolean isNetWork(){
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect)){
            return true;
        }else{
            return false;
        }
    }

    //wifi 이용하는가
    public static boolean IsWifiAvailable(Context context)
    {
        ConnectivityManager m_NetConnectMgr= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean bConnect = false;
        try
        {
            if( m_NetConnectMgr == null ) return false;

            NetworkInfo info = m_NetConnectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            bConnect = (info.isAvailable() && info.isConnected());

        }
        catch(Exception e)
        {
            return false;
        }

        return bConnect;
    }

    //3G 이용하는가
    public static boolean Is3GAvailable(Context context)
    {
        ConnectivityManager m_NetConnectMgr= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean bConnect = false;
        try
        {
            if( m_NetConnectMgr == null ) return false;
            NetworkInfo info = m_NetConnectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            bConnect = (info.isAvailable() && info.isConnected());
        }
        catch(Exception e)
        {
            return false;
        }

        return bConnect;
    }


}