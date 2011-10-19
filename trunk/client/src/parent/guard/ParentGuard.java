package parent.guard;

import parent.guard.activity.AssetListActivity;

public class ParentGuard extends AssetListActivity {
  @Override
  protected void setListContentView() {
    this.setContentView(R.layout.activity_list);
  }
}
