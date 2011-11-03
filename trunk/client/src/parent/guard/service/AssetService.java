package parent.guard.service;

import java.util.List;

import parent.guard.ServiceLocator;
import parent.guard.model.AndroidAsset;
import parent.guard.task.AsynchResponse;
import parent.guard.task.BooleanResultContainer;

public class AssetService extends BaseService {
  public AsynchResponse getAssets(List<AndroidAsset> pAndroidAssets,
      String pRequestId) {
    BooleanResultContainer tResultContainer = new BooleanResultContainer();
    ServiceLocator.getPackageDetector().getInstalledApplications(pAndroidAssets);
    tResultContainer.setResult(true, AsynchResponse.SUCCESS);
    return tResultContainer.getAsynchResponse();
  }
  
  public boolean isAssetRestricted(String pPackageName) {
    return getRestrictionPreferenceAsBoolean(pPackageName);
  }
  
  public boolean setRestricted(String pPackageName, boolean pIsRestricted) {
    return setRestrictionPreference(pPackageName, pIsRestricted);
  }
}
