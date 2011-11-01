package parent.guard.content;

import java.util.List;

import parent.guard.model.AndroidAsset;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class PackageDetector {
  private Context mContext;
  private PackageManager mPackageManager;
  
  private static final String[] WICKED_PERMISSIONS = {
    "android.permission.RESTART_PACKAGES",
    "android.permission.KILL_BACKGROUND_PROCESSES"
  };
  
  public PackageDetector(Context pContext) {
    mContext = pContext;
    mPackageManager = mContext.getPackageManager();
  }

  public void getInstalledApplications(List<AndroidAsset> pAndroidAssets) {
    pAndroidAssets.clear();
    
    Intent tMainLauncherIntent = new Intent(Intent.ACTION_MAIN);
    tMainLauncherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    List<ResolveInfo> tResolveInfos = mPackageManager.queryIntentActivities(
        tMainLauncherIntent, PackageManager.PERMISSION_GRANTED);
    
    for(ResolveInfo tResolveInfo : tResolveInfos) {
      ActivityInfo tActivityInfo = tResolveInfo.activityInfo;
      String tPackageName = tActivityInfo.packageName;
      String tActivityName = tActivityInfo.name;
      String tLabel = tActivityInfo.loadLabel(mPackageManager).toString();
      Drawable tIcon = tActivityInfo.loadIcon(mPackageManager);
      AndroidAsset tAndroidAsset = new AndroidAsset(tActivityName, 
          tPackageName, tLabel, tIcon);
      pAndroidAssets.add(tAndroidAsset);
    }
  }
  
  public boolean existsApplication(String pActivityName, String pPackageName) {
    ComponentName tComponent = new ComponentName(pPackageName, pActivityName);
    Intent tIntent = new Intent(Intent.ACTION_MAIN);
    tIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    tIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    List<ResolveInfo> tResolveInfos = mPackageManager.queryIntentActivityOptions(
        tComponent, null, tIntent, PackageManager.PERMISSION_GRANTED);
    
    return (tResolveInfos.size() > 0);
  }
  
  public boolean isWickedApplication(String pPackageName) {
    for(String tPermission : WICKED_PERMISSIONS) {
      int tStatus = mPackageManager.checkPermission(tPermission, pPackageName);
      if(tStatus == PackageManager.PERMISSION_GRANTED) {
        return true;
      }
    }
    return false;
  }
  
  public Drawable getIcon(String pActivityName, String pPackageName) {
    ComponentName tComponent = new ComponentName(pPackageName, pActivityName);
    try {
      return mPackageManager.getActivityIcon(tComponent);
    } catch(NameNotFoundException e) {
      return null;
    }
  }
  
  public String getLabel(String pActivityName, String pPackageName) {
    ComponentName tComponent = new ComponentName(pPackageName, pActivityName);
    ActivityInfo tActivityInfo = null;
    try {
      tActivityInfo = mPackageManager.getActivityInfo(tComponent,
          PackageManager.PERMISSION_GRANTED);
    } catch(NameNotFoundException e) {
      tActivityInfo = null;
    }
    if(tActivityInfo != null) {
      return tActivityInfo.loadLabel(mPackageManager).toString();
    }
    return null;
  }
}
