package parent.guard.content;

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
