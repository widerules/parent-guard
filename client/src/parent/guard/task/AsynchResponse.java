package parent.guard.task;

public enum AsynchResponse {
  SUCCESS(0),
  FAILURE_SERVER(1),
  FAILURE_NETWORK(2),
  FAILURE_CLIENT(3),
  FAILURE_UNKNOWN(9);
  
  private final int mValue;
  
  AsynchResponse(int pValue) {
    mValue = pValue;
  }
  
  public boolean isSuccess() {
    return (mValue == 0);
  }
  
  public boolean equals(AsynchResponse pResponseType) {
    return (mValue == pResponseType.mValue);
  }
}
