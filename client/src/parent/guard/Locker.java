package parent.guard;

import parent.guard.activity.BaseActivity;
import parent.guard.activity.PatternActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Locker extends PatternActivity {
  private String mPackageName;
  private String mActivityName;
  private ImageView mIcon;
  private TextView mLabel;
  private TextView mMessage;

  @Override
  public void onPatternDetected(String pPattern) {
    if(getPatternService().isPatternCorrected(pPattern)) {
      setResult(Activity.RESULT_OK);
      finish();
    } else {
      mPatternView.notifyWrongPattern();
      mMessage.setText(R.string.message_pattern_error);
    }
  }

  @Override
  public void setPatternView() {
    setContentView(R.layout.activity_locker);
    mIcon = (ImageView) findViewById(R.id.locker_icon);
    mLabel = (TextView) findViewById(R.id.locker_label);
    mMessage = (TextView) findViewById(R.id.pattern_message);
    
    mPackageName = getIntent().getStringExtra(BaseActivity.KEY_PACKAGE_NAME);
    mActivityName = getIntent().getStringExtra(BaseActivity.KEY_ACTIVITY_NAME);
    
    Drawable tIcon = getPackageDetector().getIcon(mActivityName, mPackageName);
    if(tIcon != null) {
      mIcon.setImageDrawable(tIcon);
    }
    String tLabel = getPackageDetector().getLabel(mActivityName, mPackageName);
    if(tLabel != null) {
      mLabel.setText(tLabel);
    }
  }

  @Override
  public void onPatternViewLaunched() {
    if(!getSystemService().getProtected()) {
      GuardApplication.debug("no pattern to block applications");
      finish();
    }
    
    String tComponent = mPackageName + "/" + mActivityName;
    if(!getAssetService().isAssetRestricted(tComponent)) {
      GuardApplication.debug("{" + mPackageName + "/" + mActivityName +
          "} is not restricted");
      finish();
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
