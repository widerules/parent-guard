package parent.guard.activity;

import parent.guard.R;
import parent.guard.task.GuardRunnable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public abstract class AssetListActivity extends BaseActivity
    implements OnAssetLoadListener {
  protected AssetAdapter mAssetAdapter;
  protected ListView mListView;
  protected LinearLayout mLoadingSplash;
  
  protected abstract void setListContentView();
  
  public void onCreate(Bundle pBundle) {
    super.onCreate(pBundle);
    setListContentView();
    mListView = (ListView) findViewById(R.id.list_guard);
    
    mAssetAdapter = new AssetAdapter(mContext, AssetListActivity.this);
    mListView.setAdapter(mAssetAdapter);
    mLoadingSplash = (LinearLayout) findViewById(R.id.splash_loading);
    mAssetAdapter.loadingAssets();
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
