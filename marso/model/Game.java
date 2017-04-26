package marso.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="C01Game")
public class Game implements java.io.Serializable {

    private long gameId;
    private long user1Id;
    private long user2Id;
    public static enum GameStatus { created, user1_initialized, user2_initialized, initialized, started, ended, cancelled, abandoned }
    private GameStatus status;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private long winnerUserId;
    private long loserUserId;
    private long cancelUserId;
    private long abandonUserId;

    public Game() {
        super();
    }
    public Game(long user1Id, long user2Id) {
        super();
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.status = GameStatus.created;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gameid", unique = true, nullable = false)
    public long getGameId() {
        return gameId;
    }
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
	
    @Column(name="user1id", unique = true, nullable = false)
    public long getUser1Id() {
        return user1Id;
    }
    public void setUser1Id(long user1Id) {
        this.user1Id = user1Id;
    }	
	
    @Column(name="user2id", unique = true, nullable = false)
    public long getUser2Id() {
        return user2Id;
    }
    public void setUser2Id(long user2Id) {
        this.user2Id = user2Id;
    }

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time", nullable = true)
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time", nullable = true)
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }	

    @Column(name="winner_userid", unique = true, nullable = false)
    public long getWinnerUserId() {
        return winnerUserId;
    }
    public void setWinnerUserId(long winnerUserId) {
        this.winnerUserId = winnerUserId;
    }	
	
    @Column(name="loser_userid", unique = true, nullable = false)
    public long getLoserUserId() {
        return loserUserId;
    }
    public void setLoserUserId(long loserUserId) {
        this.loserUserId = loserUserId;
    }
	
    @Column(name="cancel_userid", unique = true, nullable = false)
    public long getCancelUserId() {
        return cancelUserId;
    }
    public void setCancelUserId(long cancelUserId) {
        this.cancelUserId = cancelUserId;
    }
	
    @Column(name="abandon_userid", unique = true, nullable = false)
    public long getAbandonUserId() {
        return abandonUserId;
    }
    public void setAbandonUserId(long abandonUserId) {
        this.abandonUserId = abandonUserId;
    }
}
