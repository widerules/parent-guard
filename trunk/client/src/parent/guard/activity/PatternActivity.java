package parent.guard.activity;

import parent.guard.R;
import parent.guard.ServiceLocator;
import parent.guard.service.PatternService;
import android.widget.TextView;

public abstract class PatternActivity extends BaseActivity implements
    PatternView.OnPatternListener {
  protected TextView mMessage;
  private int mTextColor;
  protected PatternView mPatternView;
  
  protected abstract void setPatternView();
  protected abstract void onPatternViewLaunched();
  protected abstract void onPatternDetected(String pPattern);
  
  @Override
  protected void setActivityView() {
    setPatternView();
    mPatternView = (PatternView) findViewById(R.id.locker_pattern);
    mPatternView.setOnPatternListener(PatternActivity.this);
    mMessage = (TextView) findViewById(R.id.pattern_message);
    mTextColor = mMessage.getCurrentTextColor();
    onPatternViewLaunched();
  }

  protected PatternService getPatternService() {
    return ServiceLocator.getPatternService();
  }
  
  public void onPatternCleared() {
  }
  
  public void onPatternDrawn(String pPattern) {
    onPatternDetected(pPattern);
  }
  
  public void onPatternStart() {
  }
  
  protected void setMessage(int pResourceId, boolean pError) {
    if(pError) {
      mPatternView.notifyWrongPattern();
      mMessage.setTextColor(getResources().getColor(R.color.error));
    } else {
      mMessage.setTextColor(mTextColor);
    }
    mMessage.setText(pResourceId);
  }
}
