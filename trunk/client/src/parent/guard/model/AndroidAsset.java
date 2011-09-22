package parent.guard.model;

import android.graphics.drawable.Drawable;

public class AndroidAsset {
  private String mActivityName;
  private String mPackageName;
  private String mLabel;
  private Drawable mIcon;
  private boolean mIsProhibited;
  
  public AndroidAsset(String pActivityName, String pPackageName, String pLabel,
      Drawable pIcon) {
    mActivityName = pActivityName;
    mPackageName = pPackageName;
    mLabel = pLabel;
    mIcon = pIcon;
    mIsProhibited = false;
  }
  
  public void setProhibited(boolean pIsProhibited) {
    mIsProhibited = pIsProhibited;
  }
  
  public String getActivityName() {
    return mActivityName;
  }
  
  public String getPackageName() {
    return mPackageName;
  }
  
  public String getLabel() {
    return mLabel;
  }
  
  public Drawable getIcon() {
    return mIcon;
  }
  
  public boolean isProhibited() {
    return mIsProhibited;
  }
}
