package parent.guard.content;

import java.util.ArrayList;
import java.util.List;
import parent.guard.model.AndroidAsset;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Intent ;
import android.content.pm.ResolveInfo ;


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
    	mMainApps = refreshInstalledApplications() ; 
	
    }
    

    public List<ResolveInfo> getResolveInfo( ) {
		
    	return mResolveInfo; 
    	
    }

    public void setResolveInfo( Intent lIntent, int lFlag ){ 
    	
    	mResolveInfo = mPackageManager.queryIntentActivities( lIntent , lFlag ) ;
    }
    
    
  public List<AndroidAsset> refreshInstalledApplications() {
    
      List<AndroidAsset> lApps = new ArrayList<AndroidAsset>() ;
    	
    	for( ResolveInfo lInfo : mResolveInfo ){ 
       
       AndroidAsset lAsset = new AndroidAsset() ;
    	
       lAsset.setComponent( lInfo.activityInfo.name , lInfo.activityInfo.packageName );
       
       lAsset.setLabelAndIcon( lInfo.activityInfo.name, lInfo.activityInfo.packageName, 
                               lInfo.loadLabel(mPackageManager).toString() , lInfo.loadIcon(mPackageManager) ) ;
       
       lApps.add( lAsset ); 
       
       
       }  
    	 
    	mMainApps = lApps ;
    	
    	return mMainApps ;
  
  
    }
    
    public List<AndroidAsset> getInstalledApplications( ) {
    	
    	return mMainApps ;
    	
    }

    public List<PackageInfo> getInstalledPackages(int pFlag ) {
    	
    	return mPackageManager.getInstalledPackages( pFlag  );
    	
    }
    
    
    
    public PackageManager getPackageManager() {
	
    	return mPackageManager;
	
    }
    
    
}
