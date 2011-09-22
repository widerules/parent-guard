package parent.guard.task;

public interface AsynchRequest {
  public String getRequestId();
  public boolean getCache();
  public AsynchResponse doRequest();
  public void onResponse();
  public void onException(AsynchResponse pResponseType);
}
