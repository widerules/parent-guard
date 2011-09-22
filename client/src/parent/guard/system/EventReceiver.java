package parent.guard.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EventReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context pContext, Intent pIntent) {
    if(Intent.ACTION_BOOT_COMPLETED.equals(pIntent.getAction())) {
    }
  }
}
