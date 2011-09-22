package parent.guard.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestManager {
  private ExecutorService mTaskExecutor;
  public RequestManager() {
    mTaskExecutor = Executors.newSingleThreadExecutor();
  }
  
  public void runTaskRequest(AsynchRequest pAsynchRequest) {
    AsynchRequestRunnable tRunnable = new AsynchRequestRunnable(
        pAsynchRequest);
    mTaskExecutor.execute(tRunnable);
  }
  
  private class AsynchRequestRunnable implements Runnable {
    private AsynchRequest mAsynchRequest;
    
    public AsynchRequestRunnable(AsynchRequest pAsynchRequest) {
      mAsynchRequest = pAsynchRequest;
    }
    
    public void run() {
      if(mAsynchRequest.getCache()) {
        mAsynchRequest.onResponse();
      } else {
        AsynchResponse tResponseType = mAsynchRequest.doRequest();
        if(tResponseType.isSuccess()) {
          mAsynchRequest.onResponse();
        } else {
          mAsynchRequest.onException(tResponseType);
        }
      }
    }
  }
}
