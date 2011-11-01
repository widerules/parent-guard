package parent.guard;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import parent.guard.R;
import parent.guard.activity.BaseActivity;

public class PatternHelper extends BaseActivity implements OnClickListener {
  private Button mCancel;
  private Button mNext;
  
  @Override
  protected void onActivityResult(int pRequestCode, int pResultCode,
      Intent pIntent) {
    super.onActivityResult(pRequestCode, pResultCode, pIntent);
    if(pRequestCode == ACTIVITY_PATTERN_EXAMPLE) {
      if(pResultCode == RESULT_OK) {
        setResult(RESULT_OK);
        finish();
      }
    }
  }

  @Override
  protected void setActivityView() {
    setContentView(R.layout.activity_helper);
    mCancel = (Button) findViewById(R.id.button_cancel);
    mCancel.setOnClickListener(PatternHelper.this);
    mNext = (Button) findViewById(R.id.button_next);
    mNext.setOnClickListener(PatternHelper.this);
  }

  public void onClick(View pView) {
    if(pView.getId() == R.id.button_cancel) {
      setResult(RESULT_CANCELED);
      finish();
    } else if(pView.getId() == R.id.button_next) {
      startPatternExample();
    }
  }
}
