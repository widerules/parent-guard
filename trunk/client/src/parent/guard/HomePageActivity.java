
package parent.guard;

import parent.guard.activity.BaseActivity;
import android.content.Intent;
import android.os.Bundle;

public class HomePageActivity extends BaseActivity  {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	if(!getSystemService().getProtected()) {
            startPatternDrawer(true);
          }
    }    
    
 @Override
 protected void onActivityResult(int pRequestCode, int pResultCode, Intent pIntent) {
   super.onActivityResult(pRequestCode, pResultCode, pIntent);
   
   if(pRequestCode == ACTIVITY_PATTERN_DRAWER) {
	   if(pResultCode == RESULT_CANCELED) {
		   	finish();
	   } else if (pResultCode == RESULT_OK) {
		   setActivityView();
	   }
   	}	
 }

@Override
protected void setActivityView() {
	setContentView( R.layout.settings_main ) ;
}
 
}
