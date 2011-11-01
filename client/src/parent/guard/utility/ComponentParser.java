package parent.guard.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Build;

import parent.guard.model.AndroidAsset;

public class ComponentParser {
  private static final String PREFIX_3 = "Starting activity: Intent {";
  private static final String PREFIX_9 = "Starting: Intent {";
  private static final String COMPONENT_SPLIT = "/";
  private static final String COMPONENT_DOT = ".";
  
  private static final String PATTERN_ACTION_3 = 
    "action=([a-zA-Z\\.\\_\\$0-9]+)";
  private static final String PATTERN_ACTION_4 = 
    "act=([a-zA-Z\\.\\_\\$0-9]+)";
  private static final String PATTERN_CATEGORIES_3 = 
    "categories=\\{([a-zA-Z\\.\\_\\$0-9]+)\\}";
  private static final String PATTERN_CATEGORIES_4 = 
    "cat=\\[([a-zA-Z\\.\\_\\$0-9]+)\\]";
  private static final String PATTERN_FLAGS_3 =
    "flags=([0][x][$0-9]+)";
  private static final String PATTERN_FLAGS_4 =
    "flg=([0][x][$0-9]+)";
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
    boolean tIsStarting = false;
    int version = Integer.valueOf(Build.VERSION.SDK);
    if(version > 8) {
      tIsStarting = pComponent.startsWith(PREFIX_9);
    } else {
      tIsStarting = pComponent.startsWith(PREFIX_3);
    }
    
    if(tIsStarting) {
      String tCategories = getCategories(pComponent);
      boolean tIsLauncher = (Intent.CATEGORY_LAUNCHER.equals(tCategories) || 
          (tCategories == null));
      boolean tIsActionMain = Intent.ACTION_MAIN.equals(getAction(pComponent));
      int tFlags = getFlags(pComponent);
      boolean tIsNewTask = ((tFlags & Intent.FLAG_ACTIVITY_NEW_TASK) > 0);
      
      if(tIsActionMain && tIsLauncher && tIsNewTask) {
        String tComponentValue = getComponent(pComponent);
        String[] tComponents = tComponentValue.split(COMPONENT_SPLIT);
        String tPackageName = tComponents[0];
        String tActivityName = tComponents[1];
        if(tActivityName.startsWith(COMPONENT_DOT)) {
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
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return tComponent.substring(7);
      }
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_ACTION_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return tComponent.substring(4);
      }
    }
    return null;
  }
  
  public String getCategories(String pComponent) {
    if(Build.VERSION.SDK.equals("3")) {
      Pattern tPattern = Pattern.compile(PATTERN_CATEGORIES_3);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return tComponent.substring(12, tComponent.length() - 1);
      }
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_CATEGORIES_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return tComponent.substring(5, tComponent.length() - 1);
      }
    }
    return null;
  }
  
  public int getFlags(String pComponent) {
    if(Build.VERSION.SDK.equals("3")) {
      Pattern tPattern = Pattern.compile(PATTERN_FLAGS_3);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return Integer.parseInt(tComponent.substring(8), 16);
      }
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_FLAGS_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return Integer.parseInt(tComponent.substring(6), 16);
      }
    }
    return 0;
  }
  
  public String getComponent(String pComponent) {
    if(Build.VERSION.SDK.equals("3")) {
      Pattern tPattern = Pattern.compile(PATTERN_COMPONENT_3);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return tComponent.substring(6, tComponent.length() - 1);
      }
    } else {
      Pattern tPattern = Pattern.compile(PATTERN_COMPONENT_4);
      Matcher tMatcher = tPattern.matcher(pComponent);
      boolean tHasFound = tMatcher.find();
      if(tHasFound) {
        String tComponent = tMatcher.group();
        return tComponent.substring(4);
      }
    }
    return null;
  }
}
