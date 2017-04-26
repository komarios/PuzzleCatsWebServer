package marso.model;

import marso.model.User;
import marso.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.List;

public class UserDAO {

        public List<User> getAllUsers(){
                List<User> data = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        data = (List<User>) session.createQuery("from User").list();
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

	public User getUserByIdTimed(long id){
                User data = null;
				String output = "";
				long start = System.currentTimeMillis();
                Session session = HibernateUtil.getSessionFactory().openSession();
				long medium = start;
				long last   = System.currentTimeMillis();
				output += " openSession: "+(last-medium)+"\n";
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
						medium = last;
						last = System.currentTimeMillis();
						output += " beginTransaction: "+(last-medium)+"\n";
                        data = (User) session.get(User.class, id);
						medium = last;
						last = System.currentTimeMillis();
						output += " session.get(User.class: "+(last-medium)+"\n";
                        tx.commit();
						medium = last;
						last = System.currentTimeMillis();
						output += " commit: "+(last-medium)+"\n";
                } catch(HibernateException e) {
                        if (tx!=null)
                                tx.rollback();
                        e.printStackTrace();
                } finally{
                        session.close();
						medium = last;
						last = System.currentTimeMillis();
						output += " session.close: "+(last-medium)+"\n";
                }
				output += " TOTAL: "+(last-start)+"\n";				
				org.apache.log4j.Logger.getLogger(UserDAO.class).error("getUserById:TIMINGS:\n"+output);
                return data;
        }
	
        public User getUserById(long id){
                User data = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        data = (User) session.get(User.class, id);
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

        public User saveUser(User u) throws org.hibernate.exception.ConstraintViolationException {
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        if(u.getUserId() == 0){
                                long id = (long) session.save(u);
                                u = (User) session.get(User.class, id);
                        }else{
                                session.update(u);
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
                return u;
        }

        public void deleteUser(User u){
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                try{
                        tx = session.beginTransaction();
                        session.delete(u);
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
