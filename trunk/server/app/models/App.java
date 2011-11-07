package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(
    uniqueConstraints=
      @UniqueConstraint(columnNames={"packageName","activityName"})
)
public class App extends Model{
  @Column(name="packageName")
  public String packageName;
  @Column(name= "activityName")
  public String activityName;
  
  // app table properties
  public String appName;
  public float ageLevel;
}
