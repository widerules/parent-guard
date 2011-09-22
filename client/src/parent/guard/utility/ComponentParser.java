package parent.guard.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Build;

import parent.guard.model.AndroidAsset;

public class ComponentParser {
  private static final String PREFIX = "Starting activity: Intent {";
  private static final String COMPONENT_SPLITTER = "/";
  
  private static final String PATTERN_ACTION_3 = 
    "action=([a-zA-Z\\.\\_\\$0-9]+)";
  private static final String PATTERN_ACTION_4 = 
    "act=([a-zA-Z\\.\\_\\$0-9]+)";
  private static final String PATTERN_CATEGORIES_3 = 
    "categories=\\{([a-zA-Z\\.\\_\\$0-9]+)\\}";
  private static final String PATTERN_CATEGORIES_4 = 
    "cat=\\[([a-zA-Z\\.\\_\\$0-9]+)\\]";
  private static final String PATTERN_COMPONENT_3 = 
    "comp=\\{([a-zA-Z\\.\\_\\$0-9]+)\\/([a-zA-Z\\.\\_\\$0-9]+)\\}";
  private static final String PATTERN_COMPONENT_4 = 
    "cmp=([a-zA-Z\\.\\_\\$0-9]+)\\/([a-zA-Z\\.\\_\\$0-9]+)";
  
  private static ComponentParser sComponentParser;
  
  private ComponentParser() {
    
  }
  
  public static synchronized ComponentParser getDefault() {
    if(sComponentParser == null) {
      sComponentParser = new ComponentParser();
    }
    return sComponentParser;
  }
  
  public boolean parser(String pComponent, AndroidAsset pAndroidAsset) {
    if(pComponent.startsWith(PREFIX)) {
      if(Intent.ACTION_MAIN.equals(getAction(pComponent)) &&
          Intent.CATEGORY_LAUNCHER.equals(getCategories(pComponent))) {
        String tComponentValue = getComponent(pComponent);
        String[] tComponents = tComponentValue.split("/");
        String tPackageName = tComponents[0];
        String tActivityName = tComponents[1];
        if(tActivityName.startsWith(COMPONENT_SPLITTER)) {
          tActivityName = tPackageName + tActivityName;
        }
        pAndroidAsset.setComponent(tActivityName, tPackageName);
        return true;
      }
      return false;
    }
    return false;
  }
  
  public String getAction(String pComponent) {
    if(Build.VERSION.SDK.equals("3")) {
      Pattern tPattern = Pattern.compile(PATTERN_ACTION_3);
      Matcher tMatcher = tPattern.matcher(pComponent);
      tMatcher.find();
      String tComponent = tMatcher.group();
      return tComponent.substring(7);
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_ACTION_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      tMatcher.find();
      String tComponent = tMatcher.group();
      return tComponent.substring(4);
    }
  }
  
  public String getCategories(String pComponent) {
    if(Build.VERSION.SDK.equals("3")) {
      Pattern tPattern = Pattern.compile(PATTERN_CATEGORIES_3);
      Matcher tMatcher = tPattern.matcher(pComponent);
      tMatcher.find();
      String tComponent = tMatcher.group();
      return tComponent.substring(12, tComponent.length() - 1);
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_CATEGORIES_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      tMatcher.find();
      String tComponent = tMatcher.group();
      return tComponent.substring(5, tComponent.length() - 1);
    }
  }
  
  public String getComponent(String pComponent) {
    if(Build.VERSION.SDK.equals("3")) {
      Pattern tPattern = Pattern.compile(PATTERN_COMPONENT_3);
      Matcher tMatcher = tPattern.matcher(pComponent);
      tMatcher.find();
      String tComponent = tMatcher.group();
      return tComponent.substring(6, tComponent.length() - 1);
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_COMPONENT_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      tMatcher.find();
      String tComponent = tMatcher.group();
      return tComponent.substring(4);
    }
  }
}
