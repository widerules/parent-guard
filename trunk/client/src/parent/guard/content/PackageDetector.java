package parent.guard.content;

import java.util.ArrayList;
import java.util.List;
import parent.guard.model.AndroidAsset;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Intent ;
import android.content.pm.ResolveInfo ;

/* Programmer: Drew Morrissey 
 * 
 * File: PackageDetector.java
 * 
 * Date: 9/27/2011  
 * 
 * Version 1.0 */

public class PackageDetector {
	
  private Context mContext;
  private PackageManager mPackageManager;
  private List<ResolveInfo> mResolveInfo ;
  private List<AndroidAsset> mMainApps ;
  private Intent mIntent ;
  
    public PackageDetector(Context pContext) {
	
    	mContext = pContext;
    	mPackageManager = mContext.getPackageManager();
    	mIntent = new Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER);
    	mResolveInfo = mPackageManager.queryIntentActivities( mIntent, PackageManager.PERMISSION_GRANTED );
    	mMainApps = getInstalledApplications() ; 
	
    }
    
    /* Returns the list of ResolveInfo's */
    public List<ResolveInfo> getResolveInfo( ) {
		
    	return mResolveInfo; 
    	
    }
    /* Not needed but might be by other classes */
    public void setResolveInfo( Intent lIntent, int lFlag ){ 
    	
    	mResolveInfo = mPackageManager.queryIntentActivities( lIntent , lFlag ) ;
    }
    
    /*This updates mMainApps every time it is called, 
     * in case other applications were installed in time between last function call and the current*/
    
    public List<AndroidAsset> getInstalledApplications( ) {
    	
    	List<AndroidAsset> lApps = new ArrayList<AndroidAsset>() ;
    	
    	for( ResolveInfo lInfo : mResolveInfo ){ lApps.add( getAsset( lInfo ) ); }  
    	 
    	mMainApps = lApps ;
    	
    	return mMainApps ;
    	
    }
    /*Utility to get all installed packages. May not needed. */
    
    public List<PackageInfo> getInstalledPackages(int pFlag ) {
    	
    	return mPackageManager.getInstalledPackages( pFlag  );
    	
    }
    
    
    /*This method can be public or private just to make code look neater in the function above*/
    public AndroidAsset getAsset( ResolveInfo pInfo ) {
    	
    	AndroidAsset lAsset = new AndroidAsset() ;
    	
    	lAsset.setComponent( pInfo.activityInfo.name , pInfo.activityInfo.packageName );
    	lAsset.setLabelAndIcon(
    			pInfo.activityInfo.name, 
    			pInfo.activityInfo.packageName, 
    			pInfo.loadLabel(mPackageManager).toString() , 
    			pInfo.loadIcon(mPackageManager )) ;
    	
    	return lAsset ;
    }
    
    public PackageManager getPackageManager() {
	
    	return mPackageManager;
	
    }
    
    
}

  /*Old Code:*/
/* package parent.guard.content;

import android.content.Context;
import android.content.pm.PackageManager;

public class PackageDetector {
  private Context mContext;
  private PackageManager mPackageManager;
  
  public PackageDetector(Context pContext) {
    mContext = pContext;
    mPackageManager = mContext.getPackageManager();
  }
  
  public PackageManager getPackageManager() {
    return mPackageManager;
  }
}
*/