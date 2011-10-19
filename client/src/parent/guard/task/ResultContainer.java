package parent.guard.task;

public abstract class ResultContainer {
  protected AsynchResponse mAsynchResponse;
  
  public static final int TYPE_STRING = 0;
  public static final int TYPE_BOOLEAN = 1;
  public static final int TYPE_IMAGE = 2;
  
  public abstract int getContainerType();
  public abstract void setResult(Object tObject, AsynchResponse pAsynchResponse);
  public abstract Object getResult();
  
  public ResultContainer() {
    mAsynchResponse = AsynchResponse.FAILURE_UNKNOWN;
  }
  
  public AsynchResponse getAsynchResponse() {
    return mAsynchResponse;
  }
  
  public void setResult(AsynchResponse pAsynchResponse) {
    mAsynchResponse = pAsynchResponse;
  }
  
  public boolean isSuccess() {
    return mAsynchResponse.isSuccess();
  }
}
