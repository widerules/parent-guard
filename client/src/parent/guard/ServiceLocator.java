package parent.guard;

import parent.guard.content.PackageDetector;
import parent.guard.service.AssetService;
import parent.guard.service.PatternService;
import parent.guard.service.SystemService;
import parent.guard.task.RequestManager;
import android.content.Context;

public class ServiceLocator {
  private static boolean sIsInitialized = false;
  private static PackageDetector sPackageDetector;
  private static RequestManager sRequestManager;
  private static AssetService sAssetService;
  private static PatternService sPatternService;
  private static SystemService sSystemService;
  
  public static void initilize(Context pContext) {
    if(!sIsInitialized) {
      sIsInitialized = true;
      sPackageDetector = new PackageDetector(pContext);
      sRequestManager = new RequestManager();
      sAssetService = new AssetService();
      sPatternService = new PatternService();
      sSystemService = new SystemService();
    }
  }
  
  public static void destroy() {
    sIsInitialized = false;
    sPackageDetector = null;
    sRequestManager = null;
    sAssetService = null;
    sPatternService = null;
    sSystemService = null;
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
  
  public static PatternService getPatternService() {
    return sPatternService;
  }
  
  public static SystemService getSystemService() {
    return sSystemService;
  }
}
