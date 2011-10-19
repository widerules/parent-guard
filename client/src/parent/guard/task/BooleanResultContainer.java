package parent.guard.task;

public class BooleanResultContainer extends ResultContainer {
  private boolean mResultBoolean;
  
  public BooleanResultContainer() {
    mResultBoolean = false;
  }
  
  @Override
  public int getContainerType() {
    return TYPE_BOOLEAN;
  }
  
  @Override
  public Boolean getResult() {
    return mResultBoolean;
  }
  
  @Override
  public void setResult(Object tObject, AsynchResponse pAsynchResponse) {
    mResultBoolean = (Boolean) tObject;
    mAsynchResponse = pAsynchResponse;
  }
}
