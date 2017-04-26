package marso.model;

import org.hibernate.Query;
import marso.model.Game;
import marso.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.List;

public class GameDAO {

	public void createNewGame() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query q = session.createQuery("from Lobby order by enterTime");
			q.setMaxResults(2);
			List<Lobby> data = (List<Lobby>) q.list();
			if( data.size() == 2 ){
				long user1id = data.get(0).getUserId();
				long user2id = data.get(1).getUserId();
				Game game = new Game( user1id, user2id );
				session.save( game );
				session.delete( data.get(0) );
				session.delete( data.get(1) );
			}
			tx.commit();
		} catch(HibernateException e) {
			if (tx!=null)
				tx.rollback();
				e.printStackTrace();
		} finally{
			session.close();
		}
	}

        public List<Game> getGamesByUserIdAndStatus(long userId, Game.GameStatus status){
                List<Game> data = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
			Query q = session.createQuery("from Game where status = :var_status AND ( user1Id = :var_userid OR user2Id = :var_userid )");
			q.setParameter("var_userid", userId);
			q.setParameter("var_status", status);
			data = (List<Game>) q.list();
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

        public Game getGameById(long id){
                Game data = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        data = (Game) session.get(Game.class, id);
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

        public Game saveGame(Game o) throws org.hibernate.exception.ConstraintViolationException {
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        if(o.getGameId() == 0){
                                long id = (long) session.save(o);
                                o = (Game) session.get(Game.class, id);
                        }else{
                                session.update(o);
                        }
                        tx.commit();
                } catch(org.hibernate.exception.ConstraintViolationException unqex){
                        throw unqex;
                } catch(HibernateException e) {
                        if (tx!=null)
                                tx.rollback();
                        e.printStackTrace();
                } finally{
                        session.close();
                }
                return o;
        }

        public void deleteGame(Game o){
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        session.delete(o);
                        tx.commit();
                } catch(HibernateException e) {
                        if (tx!=null)
                                tx.rollback();
                        e.printStackTrace();
                } finally{
                        session.close();
                }
        }
}
