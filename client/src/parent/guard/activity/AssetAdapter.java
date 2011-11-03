package parent.guard.activity;

import java.util.ArrayList;
import java.util.List;

import parent.guard.R;
import parent.guard.ServiceLocator;
import parent.guard.model.AndroidAsset;
import parent.guard.service.AssetService;
import parent.guard.task.AsynchRequest;
import parent.guard.task.AsynchResponse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AssetAdapter extends BaseAdapter {
  private Context mContext;
  private List<AndroidAsset> mAndroidAssets;
  private int mAdapterSize;
  private OnAssetLoadListener mOnAssetLoadListener;
  
  public AssetAdapter(Context pContext, OnAssetLoadListener pListener) {
    mContext = pContext;
    mAndroidAssets = new ArrayList<AndroidAsset>();
    mOnAssetLoadListener = pListener; 
  }

  public int getCount() {
    return mAdapterSize;
  }

  public AndroidAsset getItem(int pPosition) {
    return mAndroidAssets.get(pPosition);
  }

  public long getItemId(int pPosition) {
    return pPosition;
  }

  public View getView(int pPosition, View pConvertView, ViewGroup pParent) {
    AssetHolder tAssetHolder;
    View tAssetView = pConvertView;
    if(tAssetView == null) {
      tAssetView = View.inflate(mContext, R.layout.view_asset, null);
      tAssetHolder = new AssetHolder();
      tAssetHolder.icon = (ImageView) tAssetView.findViewById(R.id.asset_icon);
      tAssetHolder.label = (TextView) tAssetView.findViewById(R.id.asset_label);
      tAssetHolder.block = (CheckBox) tAssetView.findViewById(R.id.asset_block);
      tAssetHolder.block.setFocusable(false);
      tAssetHolder.block.setClickable(false);
      tAssetView.setTag(tAssetHolder);
    } else {
      tAssetHolder = (AssetHolder) tAssetView.getTag();
    }
    
    AndroidAsset tAndroidAsset = mAndroidAssets.get(pPosition);
    Drawable tIcon = tAndroidAsset.getIcon();
    String tLabel = tAndroidAsset.getLabel();
    String tComponentName = tAndroidAsset.getComponentName();
    boolean tBlock = getAssetService().isAssetRestricted(tComponentName);
    if(tIcon == null) {
      tAssetHolder.icon.setImageResource(R.drawable.application_unknown);
    } else {
      tAssetHolder.icon.setImageDrawable(tIcon);
    }
    tAssetHolder.label.setText(tLabel);
    tAssetHolder.block.setChecked(tBlock);
    
    return tAssetView;
  }
  
  private static class AssetHolder {
    protected ImageView icon;
    protected TextView label;
    protected CheckBox block;
  }
  
  public void loadingAssets() {
    AssetRequest tAssetRequest = new AssetRequest();
    ServiceLocator.getRequestManager().runTaskRequest(tAssetRequest);
  }
  
  private void setAndroidAssets(List<AndroidAsset> pAndroidAssets) {
    mAndroidAssets.clear();
    mAndroidAssets.addAll(pAndroidAssets);
    mAdapterSize = mAndroidAssets.size();
    mOnAssetLoadListener.onFinished();
  }
  
  private class AssetRequest implements AsynchRequest {
    List<AndroidAsset> mDownloadedAssets;
    
    public AssetRequest() {
      mDownloadedAssets = new ArrayList<AndroidAsset>();
    }
    
    public AsynchResponse doRequest() {
      return getAssetService().getAssets(mDownloadedAssets, getRequestId());
    }

    public boolean getCache() {
      return false;
    }

    public String getRequestId() {
      return "installed_all";
    }

    public void onException(AsynchResponse pResponseType) {
    }

    public void onResponse() {
      setAndroidAssets(mDownloadedAssets);
    }
  }
  
  public void setRestricted(String pComponentName) {
    boolean tIsRestricted = getAssetService().isAssetRestricted(pComponentName);
    getAssetService().setRestricted(pComponentName, !tIsRestricted);
    notifyDataSetChanged();
  }
  
  private AssetService getAssetService() {
    return ServiceLocator.getAssetService();
  }
}
