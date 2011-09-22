package parent.guard;

import android.app.Activity;
import android.os.Bundle;

public class ParentGuard extends Activity {
  
  @Override
  public void onCreate(Bundle pBundle) {
    super.onCreate(pBundle);
    setContentView(R.layout.main);
  }
}
