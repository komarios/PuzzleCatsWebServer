package marso.model;

import marso.model.GameMove;
import marso.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.List;

public class GameMoveDAO {

	public List<GameMove> getLatestGameMoves(long gameid, long userid, long moveid ){        
		List<GameMove> data = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query q = session.createQuery("from GameMove where gameId = :var_gameid and userId = :var_userid and moveId > :var_moveid");
			q.setParameter("var_gameid", gameid);
			q.setParameter("var_userid", userid);
			q.setParameter("var_moveid", moveid);
			data = (List<GameMove>) q.list();
			tx.commit();
		} catch(HibernateException e) {
			if (tx!=null)
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
		return data;
	}

	public GameMove saveGameMove(GameMove gm) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			long id = (long) session.save(gm);
			gm = (GameMove) session.get(GameMove.class, id);
			session.refresh(gm);
			tx.commit();
		} catch(HibernateException e) {
			if (tx!=null)
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
		return gm;
	}
}
