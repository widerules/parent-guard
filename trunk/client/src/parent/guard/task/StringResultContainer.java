package parent.guard.task;

public class StringResultContainer extends ResultContainer {
  private String mResponseString;
  
  public StringResultContainer() {
    mResponseString = "";
  }
  
  @Override
  public int getContainerType() {
    return TYPE_STRING;
  }

  @Override
  public String getResult() {
    return mResponseString;
  }
  
  @Override
  public void setResult(Object tObject, AsynchResponse pAsynchResponse) {
    mResponseString = tObject.toString();
    mAsynchResponse = pAsynchResponse;
  }
}
