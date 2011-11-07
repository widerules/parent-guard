package controllers;

import play.*;
import play.db.DB;
import play.mvc.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import controllers.DataTransferProtocol.AppProperty;
import controllers.DataTransferProtocol.AppRequest;

import models.*;

public class DataHandler extends Controller {

    public static void index() {
        render();
    }
    
    public static void onReceive(){
      InputStream is = request.body;
      
      try {
        AppRequest appRequest = AppRequest.parseFrom(is);
        User user = new User();
        user.deviceId = appRequest.getDeviceId();
        user.childBirthYear = appRequest.getChildBirth();
        
        List<AppProperty> appPropertyList = appRequest.getAppsList();
        Iterator<AppProperty> it = appPropertyList.iterator();
        
        ArrayList<App> appList = new ArrayList<App>();
        ArrayList<UserAppAssociation> associationList = new ArrayList<UserAppAssociation>();
        
        while(it.hasNext()){
          App app = new App();
          AppProperty appProperty = it.next();
          app.activityName = appProperty.getAppActivityName();
          app.appName = appProperty.getAppName();
          app.packageName = appProperty.getAppPackageName();
          appList.add(app);
          
          UserAppAssociation userAppAssociation = new UserAppAssociation();
          userAppAssociation.app = app;
          userAppAssociation.user = user;
          userAppAssociation.isRestricted = appProperty.getIsRestricted();
          associationList.add(userAppAssociation);
        }
        
        user.save();
        for (App app : appList ){
          app.save();
        }
        for (UserAppAssociation userAppAssociation : associationList){
          userAppAssociation.save();
        }
        
        is.close();
      } catch(IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    
    
    public static void dropAllTables(){
      DB.execute("drop table if exists UserAppAssociation");
      DB.execute("drop table if exists User");
      DB.execute("drop table if exists App");
    }
    
    public static void dropData(){
      DB.execute("delete from UserAppAssociation");
      DB.execute("delete from User");
      DB.execute("delete from App");
    }

}