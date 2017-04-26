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
@Table(name="C03GameMove")
public class GameMove implements java.io.Serializable {

	private long moveId;
    private long gameId;
    private long userId;
    private Date moveTime;
    private String move;

    public GameMove() {
        super();
    }
    public GameMove(long gameid, long userid, String move) {
        super();
        this.gameId = gameid;
        this.userId = userid;
        this.move = move;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="moveid", unique = true, nullable = false)
    public long getMoveId() {
        return moveId;
    }
    public void setMoveId(long moveId) {
        this.moveId = moveId;
    }

    @Column(name="gameid", unique = true, nullable = false)
    public long getGameId() {
        return gameId;
    }
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    @Column(name="userid", unique = true, nullable = false)
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="move_time", nullable = true)
    public Date getMoveTime() {
        return moveTime;
    }
    public void setMoveTime(Date moveTime) {
        this.moveTime = moveTime;
    }

    @Column(name="move", unique = true, nullable = false)
    public String getMove() {
        return move;
    }
    public void setMove(String move) {
        this.move = move;
    }
}
