package parent.guard;

import android.content.Intent;
import parent.guard.activity.AssetListActivity;

public final class ParentGuard extends AssetListActivity {
  @Override
  protected void setListContentView() {
    setContentView(R.layout.activity_list);
  }

@Override
protected void onListLaunched() {
	
	
}

 /* @Override
  protected void onListLaunched() {
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
  */
  
}
