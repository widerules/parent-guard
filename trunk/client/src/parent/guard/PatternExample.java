package parent.guard;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import parent.guard.activity.BaseActivity;

public class PatternExample extends BaseActivity implements OnClickListener {
  private static final long ANIMATION_DELAY = 1000;
  private Button mCancel;
  private Button mNext;
  private ImageView mImageView;
  private AnimationDrawable mAnimationDrawable;
  private Handler mHandler;

  @Override
  protected void setActivityView() {
    setContentView(R.layout.activity_example);
    mCancel = (Button) findViewById(R.id.button_cancel);
    mCancel.setOnClickListener(PatternExample.this);
    mNext = (Button) findViewById(R.id.button_next);
    mNext.setOnClickListener(PatternExample.this);
    mImageView = (ImageView) findViewById(R.id.example_animation);
    mImageView.setBackgroundResource(R.drawable.pattern_animation);
    mAnimationDrawable = (AnimationDrawable) mImageView.getBackground();
    mHandler = new Handler();
  }
  
  @Override
  protected void onPause() {
    super.onPause();
    stopAnimation();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mHandler.postDelayed(mRunnable, ANIMATION_DELAY);
  }

  public void onClick(View pView) {
    if(pView.getId() == R.id.button_cancel) {
      setResult(RESULT_CANCELED);
      finish();
    } else if(pView.getId() == R.id.button_next) {
      stopAnimation();
      setResult(RESULT_OK);
      finish();
    }
  }
  
  private void startAnimation() {
    if((mAnimationDrawable != null) && !mAnimationDrawable.isRunning()) {
      mAnimationDrawable.run();
    }
  }
  
  private void stopAnimation() {
    if((mAnimationDrawable != null) && mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
  }
  
  private Runnable mRunnable = new Runnable() {
    public void run() {
      startAnimation();
    }
  };
}
