package br.com.blue;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.provider.Settings;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import android.content.Context;
import android.location.LocationManager;
import android.os.Environment;
import android.os.StatFs;

/**
 * This class echoes a string called from JavaScript.
 */
public class FreeSpace extends CordovaPlugin {

	boolean gpsEnabled = false;
	PluginResult result = null;
	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getEspacoLivrePercent")) {
            //String message = args.getString(0);
            this.getEspacoLivrePercent(callbackContext);
            return true;
        }else if (action.equals("getGPSAtivo")) {
            //String message = args.getString(0);
            this.getGPSAtivo(callbackContext);
            return true;
        }
        return false;
    }
	public long getAvailableSpaceInMB(){
	    long SIZE_KB = 1024L;
	    long SIZE_MB = SIZE_KB * SIZE_KB;
	    long availableSpace = -1L;
	    StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
	    availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
	    return availableSpace/SIZE_MB;
	  }
	  
	  public long getTotalSpaceInMB(){
	    long SIZE_KB = 1024L;
	    long SIZE_MB = SIZE_KB * SIZE_KB;
	    long total = -1L;
	    StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
	    total = (long) stat.getBlockSize() * stat.getBlockCount();
	    return total/SIZE_MB;
	  }
	  
	//final LocationManager manager = (LocationManager) Context.getSystemService(Context.LOCATION_SERVICE);\
	protected LocationManager locationManager;
	
    private void getGPSAtivo(CallbackContext callbackContext) {
    	android.content.ContentResolver contentResolver = cordova.getActivity().getApplicationContext().getContentResolver();
    	gpsEnabled = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
    	result = new PluginResult(Status.OK, gpsEnabled);
    	
    	callbackContext.success( result.getStatus()+ " - "+result.getMessage() );

	}  
    private void getEspacoLivrePercent(CallbackContext callbackContext) {
    	System.out.println(" ---> entrou no metrodo <---");
    	
    	long free = getAvailableSpaceInMB();
    	long tot = getTotalSpaceInMB();
    	
    	System.out.println( "free: "+free+" total: "+tot);
    	
    	
    	int per = new Long( ( free * 100)/tot ).intValue();
    	callbackContext.success( per+"%" );
    	System.out.println(" ---> fim <---");
//        if (message != null && message.length() > 0) {
//            callbackContext.success(message);
//        } else {
//            callbackContext.error("Expected one non-empty string argument.");
//        }
    }
}
