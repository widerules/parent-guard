package parent.guard;

import parent.guard.activity.BaseActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Locker extends BaseActivity {
  private String mPackageName;
  private String mActivityName;
  private ImageView mImageView;
  private TextView mTextView;
  
  @Override
  protected void onCreate(Bundle pBundle) {
    super.onCreate(pBundle);
    setContentView(R.layout.activity_locker);
    mImageView = (ImageView) findViewById(R.id.asset_icon);
    mTextView = (TextView) findViewById(R.id.asset_label);
    
    mPackageName = getIntent().getStringExtra(BaseActivity.KEY_PACKAGE_NAME);
    mActivityName = getIntent().getStringExtra(BaseActivity.KEY_ACTIVITY_NAME);
    
    if(ParentGuard.class.getPackage().getName().equals(mPackageName) && 
        ParentGuard.class.getName().equals(mActivityName)) {
      finish();
    }
    
    Drawable tIcon = getPackageDetector().getIcon(mActivityName, mPackageName);
    if(tIcon != null) {
      mImageView.setImageDrawable(tIcon);
    }
    String tLabel = getPackageDetector().getLabel(mActivityName, mPackageName);
    if(tLabel != null) {
      mTextView.setText(tLabel);
    }
  }

  @Override
  public boolean onKeyDown(int pKeyCode, KeyEvent pKeyEvent) {
    if((pKeyCode == KeyEvent.KEYCODE_BACK) &&
        (pKeyEvent.getRepeatCount() == 0)) {
      goHome();
    }
    return super.onKeyDown(pKeyCode, pKeyEvent);
  }
  
  private void goHome() {
    Intent tIntent = new Intent(Intent.ACTION_MAIN);
    tIntent.addCategory(Intent.CATEGORY_HOME);
    tIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mContext.startActivity(tIntent);
  }
}
