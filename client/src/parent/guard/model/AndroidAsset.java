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
  
  public AndroidAsset() {
    mActivityName = "";
    mPackageName = "";
    mLabel = "";
    mIcon = null;
    mIsProhibited = false;
  }
  
  public void setComponent(String pActivityName, String pPackageName) {
    mActivityName = pActivityName;
    mPackageName = pPackageName;
  }
  
  public void setLabelAndIcon(String pActivityName, String pPackageName, String pLabel,
      Drawable pIcon) {
    mLabel = pLabel;
    mIcon = pIcon;
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
  
  public String getComponentName() {
    return mPackageName + "/" + mActivityName;
  }
  
  public static AndroidAsset valueOf(Object pObject) {
    try {
      return (AndroidAsset) pObject;
    } catch(ClassCastException e) {
      return null;
    }
  }
}
