package parent.guard ;


import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.content.Intent;


public class ParentMain extends TabActivity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_tab );
	
        final TabHost tabHost = getTabHost();
       
        tabHost.addTab(tabHost.newTabSpec("tab1")
		       .setIndicator("Home", getResources().getDrawable( R.drawable.home_on  ))
		       .setContent(new Intent(this, HomePageActivity.class))) ;
       
        tabHost.addTab(tabHost.newTabSpec("tab2")
		       .setIndicator("Apps", getResources().getDrawable( R.drawable.apps_on ) )
		       .setContent(new Intent(this, ParentGuard.class))) ;
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
		       .setIndicator("Security" , getResources().getDrawable( R.drawable.security_on ))
		       .setContent(new Intent(this, SecurityActivity.class))) ;
        
        
        tabHost.addTab(tabHost.newTabSpec("tab4")
		       .setIndicator("Settings" , getResources().getDrawable( R.drawable.settings_on ))
		       .setContent(new Intent(this, SettingsActivity.class))) ;
        
    }
    
    
    
    
}


