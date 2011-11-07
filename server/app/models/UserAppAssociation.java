package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
//@IdClass(ProjectAssociation_PK.class)
public class UserAppAssociation extends Model{
  @Column
  public boolean isToDate;
  @Column
  public boolean isRestricted;
  
  
  @ManyToOne
  @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "Id")
//  @JoinColumn(name="deviceId", referencedColumnName="deviceId",insertable= false, updatable=false)
  public User user;
  
  
  @ManyToOne
  @PrimaryKeyJoinColumn(name = "appId", referencedColumnName = "Id")
  /*
  @PrimaryKeyJoinColumns({
    @PrimaryKeyJoinColumn(name = "package
    Name", referencedColumnName = "packageName"),
    @PrimaryKeyJoinColumn(name = "activityName", referencedColumnName = "activityName")
  })
  */
/*  @JoinColumns({
    @JoinColumn(name="packageName", referencedColumnName="packageName",insertable= false, updatable=false),
    @JoinColumn(name="activityName", referencedColumnName="activityName",insertable= false, updatable=false)
  })
*/
  public App app;
}
