package parent.guard;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import parent.guard.activity.PatternActivity;

public class PatternDrawer extends PatternActivity implements OnClickListener {
  private LinearLayout mPanelPattern;
  private Button mCancel;
  private Button mRetry;
  private Button mContinue;
  private Button mConfirm;
  private String mNewPattern;
  private boolean mAuthenticated;
  private boolean mConfirmNeeded;
  
  private static final int MENU_HELP = Menu.FIRST;
  
  @Override
  public boolean onCreateOptionsMenu(Menu pMenu) {
    super.onCreateOptionsMenu(pMenu);
    pMenu.add(0, MENU_HELP, Menu.NONE, R.string.menu_help).setIcon(
        R.drawable.ic_menu_help);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem pMenuItem) {
    super.onOptionsItemSelected(pMenuItem);
    switch(pMenuItem.getItemId()) {
      case MENU_HELP:
        startPatternHelper();
        return true;
    }
    return false;
  }
  
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    return mAuthenticated;
  }

  @Override
  protected void onActivityResult(int pRequestCode, int pResultCode,
      Intent pIntent) {
    super.onActivityResult(pRequestCode, pResultCode, pIntent);
    if(pRequestCode == ACTIVITY_PATTERN_HELPER) {
      if(pResultCode == RESULT_CANCELED) {
        if(!getPatternService().getPatternHelped()) {
          finish();
        }
      } else if(pResultCode == RESULT_OK) {
        mAuthenticated = true;
        drawNewPattern();
      }
    }
  }

  @Override
  protected void onPatternDetected(String pPattern) {
    if(mAuthenticated) {
      if(mConfirmNeeded) {
        if(mNewPattern.equals(pPattern)) {
          setMessage(R.string.message_pattern_confirmed, false);
          mConfirm.setEnabled(true);
        } else {
          setMessage(R.string.message_pattern_different, true);
        }
      } else {
        if(pPattern.length() < 4) {
          setMessage(R.string.message_pattern_tooshort, true);
        } else {
          mNewPattern = pPattern;
          setMessage(R.string.message_pattern_recorded, false);
          mCancel.setVisibility(View.GONE);
          mRetry.setVisibility(View.VISIBLE);
          mContinue.setEnabled(true);
        }
      }
    } else {
      if(getPatternService().isPatternCorrected(pPattern)) {
        mAuthenticated = true;
        displayPatternHelper();
      } else {
        setMessage(R.string.message_pattern_error, true);
      }
    }
  }
  
  @Override
  protected void setPatternView() {
    setContentView(R.layout.activity_pattern);
    mPanelPattern = (LinearLayout) findViewById(R.id.panel_pattern);
    mCancel = (Button) findViewById(R.id.button_cancel);
    mCancel.setOnClickListener(PatternDrawer.this);
    mRetry = (Button) findViewById(R.id.button_retry);
    mRetry.setOnClickListener(PatternDrawer.this);
    mContinue = (Button) findViewById(R.id.button_continue);
    mContinue.setOnClickListener(PatternDrawer.this);
    mConfirm = (Button) findViewById(R.id.button_confirm);
    mConfirm.setOnClickListener(PatternDrawer.this);
    mNewPattern = "";
    mAuthenticated = false;
    mConfirmNeeded = false;
  }
  
  @Override
  protected void onPatternViewLaunched() {
    boolean tIsVerified = getIntent().getBooleanExtra(KEY_PATTERN_DRAWER, false);
    if(tIsVerified) {
      mAuthenticated = true;
      displayPatternHelper();
    } else {
      mAuthenticated = false;
    }
  }
  
  public void onClick(View pView) {
    if(pView.getId() == R.id.button_cancel) {
      setResult(RESULT_CANCELED);
      finish();
    } else if(pView.getId() == R.id.button_retry) {
      mNewPattern = "";
      mPatternView.notifyClearPattern();
      setMessage(R.string.message_pattern_draw, false);
      mCancel.setVisibility(View.VISIBLE);
      mRetry.setVisibility(View.GONE);
      mContinue.setEnabled(false);
    } else if(pView.getId() == R.id.button_continue) {
      mConfirmNeeded = true;
      mPatternView.notifyClearPattern();
      setMessage(R.string.message_pattern_confirm, false);
      mCancel.setVisibility(View.VISIBLE);
      mRetry.setVisibility(View.GONE);
      mContinue.setVisibility(View.GONE);
      mConfirm.setVisibility(View.VISIBLE);
      mConfirm.setEnabled(false);
    } else if(pView.getId() == R.id.button_confirm) {
      getPatternService().setPattern(mNewPattern);
      if(!getSystemService().getProtected()) {
        getSystemService().setProtected();
      }
      
      if(!getPatternService().getPatternHelped()) {
        getPatternService().setPatternHelped();
      }
      setResult(RESULT_OK);
      finish();
    }
  }
  
  private void displayPatternHelper() {
    if(getPatternService().getPatternHelped()) {
      drawNewPattern();
    } else {
      startPatternHelper();
    }
  }
  
  private void drawNewPattern() {
    mPatternView.notifyClearPattern();
    mPanelPattern.setVisibility(View.VISIBLE);
    setMessage(R.string.message_pattern_draw, false);
  }
}
