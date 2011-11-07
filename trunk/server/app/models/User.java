package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class User extends Model{
  @Column(unique=true)
  public String deviceId;
  @Column(nullable=true)
  public String childBirthYear;
}
