package marso.model;

import org.hibernate.Query;
import marso.model.Lobby;
import marso.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.List;

public class LobbyDAO {

        public Lobby getLobbyByUserId(long userId){
                Lobby data = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
			Query q = session.createQuery("from Lobby where userId = :var_userid ");
			q.setParameter("var_userid", userId);
			data = (Lobby) q.uniqueResult();
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

        public Lobby getLobbyById(long id){
                Lobby data = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        data = (Lobby) session.get(Lobby.class, id);
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

        public Lobby saveLobby(Lobby o) throws org.hibernate.exception.ConstraintViolationException {
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        if(o.getLobbyId() == 0){
                                long id = (long) session.save(o);
                                o = (Lobby) session.get(Lobby.class, id);
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

        public void deleteLobby(Lobby o){
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
