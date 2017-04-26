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
@Table(name="B01Lobby")
public class Lobby implements java.io.Serializable {

    private long lobbyId;
    private long userId;
    private String ip;
    private Date enterTime;

    public Lobby() {
        super();
    }

    public Lobby(long userId, String ip){
        super();
        this.userId = userId;
	this.ip = ip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lobbyid", unique = true, nullable = false)
    public long getLobbyId() {
        return lobbyId;
    }
    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }
	
    @Column(name="userid", unique = true, nullable = false)
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }	

    @Column(name="ip", nullable = false, length = 50, unique=true)
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="enter_time", nullable = true)
    public Date getEnterTime() {
        return enterTime;
    }
    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

}
