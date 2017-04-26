package marso.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="A01User")
public class User implements java.io.Serializable {

    private long userId;
    private String nick;
    private String imei;
    private String gmail;
    private String token;
    private Date createdTime;
    private Date lastloginTime;

    public User() {
        super();
    }

    public User(String nick, String imei, String gmail) {
        super();
	this.nick = nick;
	this.imei = imei;
	this.gmail = gmail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid", unique = true, nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name="nick", nullable = false, length = 50, unique=true)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
	
    @Column(name="imei", nullable = false, length = 50, unique=true)
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }	
	
    @Column(name="gmail", nullable = false, length = 50, unique=true)
    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }	
	
    @Column(name="token", nullable = true, length = 50, unique=true)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }	

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_time", nullable = true)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="lastlogin_time", nullable = true)
    public Date getLastloginTime() {
        return lastloginTime;
    }

    public void setLastloginTime(Date lastloginTime) {
        this.lastloginTime = lastloginTime;
    }

}
