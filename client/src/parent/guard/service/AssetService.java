package parent.guard.service;

import java.util.List;

import parent.guard.ServiceLocator;
import parent.guard.model.AndroidAsset;
import parent.guard.task.AsynchResponse;
import parent.guard.task.BooleanResultContainer;

public class AssetService {
  public AsynchResponse getAssets(List<AndroidAsset> pAndroidAssets,
      String pRequestId) {
    BooleanResultContainer tResultContainer = new BooleanResultContainer();
    ServiceLocator.getPackageDetector().getInstalledApplications(pAndroidAssets);
    tResultContainer.setResult(true, AsynchResponse.SUCCESS);
    return tResultContainer.getAsynchResponse();
  }
}
