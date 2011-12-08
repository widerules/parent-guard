package parent.guard;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import parent.guard.activity.BaseActivity;

public class SettingsPageExample extends BaseActivity implements OnClickListener {
	private Button mNext;
	  private Button mCancel ;
	  
	  @Override
	  protected void setActivityView() {
	    setContentView(R.layout.settings_page_example);
	    mNext = (Button) findViewById(R.id.button_next);
	    mNext.setOnClickListener(SettingsPageExample.this);
	    mCancel = (Button ) findViewById( R.id.button_cancel ) ;
	    mCancel.setOnClickListener( SettingsPageExample.this ) ;
	  }  
  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public void onClick(View pView) {
    if(pView.getId() == R.id.button_next) {
      setResult(RESULT_OK);
      finish();
           
    }else if( pView.getId() == R.id.button_cancel ) {
    	setResult( RESULT_CANCELED ) ;
    	startAppsPageExample() ;
    	finish() ;
    }
    
    
  }
  
  
  
}
