package parent.guard;

//import android.content.Intent;
import parent.guard.activity.AssetListActivity;

public final class ParentGuard extends AssetListActivity {
  @Override
  protected void setListContentView() {
    setContentView(R.layout.activity_list);
  }

@Override
protected void onListLaunched() {
	
}
  
}
