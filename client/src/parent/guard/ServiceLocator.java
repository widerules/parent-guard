package parent.guard;

import parent.guard.content.PackageDetector;
import parent.guard.service.AssetService;
import parent.guard.task.RequestManager;
import android.content.Context;

public class ServiceLocator {
  private static boolean sIsInitialized = false;
  private static PackageDetector sPackageDetector;
  private static RequestManager sRequestManager;
  private static AssetService sAssetService;
  
  public static void initilize(Context pContext) {
    if(!sIsInitialized) {
      sIsInitialized = true;
      sPackageDetector = new PackageDetector(pContext);
      sRequestManager = new RequestManager();
      sAssetService = new AssetService();
    }
  }
  
  public static void destroy() {
    sIsInitialized = false;
    sPackageDetector = null;
    sRequestManager = null;
    sAssetService = null;
  }
  
  public static PackageDetector getPackageDetector() {
    return sPackageDetector;
  }
  
  public static RequestManager getRequestManager() {
    return sRequestManager;
  }
  
  public static AssetService getAssetService() {
    return sAssetService;
  }
}
