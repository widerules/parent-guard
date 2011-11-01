package parent.guard.activity;

import parent.guard.R;
import parent.guard.task.GuardRunnable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public abstract class AssetListActivity extends BaseActivity
    implements OnAssetLoadListener {
  protected AssetAdapter mAssetAdapter;
  protected ListView mListView;
  protected LinearLayout mLoadingSplash;
  
  private static final int MENU_PREFERENCES = Menu.FIRST;
  private static final int MENU_HELP = Menu.FIRST + 1;
  
  @Override
  public boolean onCreateOptionsMenu(Menu pMenu) {
    super.onCreateOptionsMenu(pMenu);
    pMenu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences).setIcon(
        R.drawable.ic_menu_preferences);
    pMenu.add(0, MENU_HELP, Menu.NONE, R.string.menu_help).setIcon(
        R.drawable.ic_menu_help);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem pMenuItem) {
    super.onOptionsItemSelected(pMenuItem);
    switch(pMenuItem.getItemId()) {
      case MENU_PREFERENCES:
        startPreferences();
        return true;
      case MENU_HELP:
        startPatternHelper();
        return true;
    }
    return false;
  }
  
  protected abstract void setListContentView();
  protected abstract void onListLaunched();
  
  @Override
  protected void setActivityView() {
    setListContentView();
    mListView = (ListView) findViewById(R.id.list_guard);
    mAssetAdapter = new AssetAdapter(mContext, AssetListActivity.this);
    mListView.setAdapter(mAssetAdapter);
    mLoadingSplash = (LinearLayout) findViewById(R.id.splash_loading);
    mAssetAdapter.loadingAssets();
    onListLaunched();
  }
  
  
  public void onException() {
  }

  public void onFinished() {
    runOnUiThread(mFinishedRunnable);
  }

  private final Runnable mFinishedRunnable = new GuardRunnable() {
    public void run() {
      mAssetAdapter.notifyDataSetChanged();
      mLoadingSplash.setVisibility(View.GONE);
      mListView.setVisibility(View.VISIBLE);
    }
  };
}
