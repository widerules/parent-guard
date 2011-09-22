package parent.guard;

import parent.guard.content.PackageDetector;
import android.content.Context;

public class ServiceLocator {
  private static boolean sIsInitialized = false;
  private static PackageDetector sPackageDetector;
  
  public static void initilize(Context pContext) {
    if(!sIsInitialized) {
      sIsInitialized = true;
      sPackageDetector = new PackageDetector(pContext);
    }
  }
  
  public static void destroy() {
    sIsInitialized = false;
  }
  
  public static PackageDetector getPackageDetector() {
    return sPackageDetector;
  }
}
