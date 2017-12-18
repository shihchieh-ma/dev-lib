package dev.majes.base.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Marl_Jar on 2017/6/7.
 */
@Entity
public class UserBean {
    @Id
    private Long id;
    @Property(nameInDb = "USERNAME")
    private String userName;
    @Property(nameInDb = "USERCOUNTY")
    private String userCounty;
    public String getUserCounty() {
        return this.userCounty;
    }
    public void setUserCounty(String userCounty) {
        this.userCounty = userCounty;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1292334811)
    public UserBean(Long id, String userName, String userCounty) {
        this.id = id;
        this.userName = userName;
        this.userCounty = userCounty;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
}
