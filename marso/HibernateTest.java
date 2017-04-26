package marso;

import org.hibernate.Session;
import org.hibernate.Query;
import java.util.List;
import marso.HibernateUtil;
import marso.model.User;

public class HibernateTest {
    public static void main( String[] args )  {
        System.out.println("TEST: Hibernate + MySQL");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        User user = new User( "HibTester", "123321123321", "tester.hib@gmail.com" );
        session.save(user);
        session.getTransaction().commit();
        session.beginTransaction();
	Query query = session.createQuery("from User");
	List<User> list = query.list();

	for(User u : list) {
			System.out.print("ID: " + u.getUserId());
			System.out.print(", nick: " + u.getNick());
			System.out.print(", imei: " + u.getImei());
			System.out.println(", gmail: " + u.getGmail());
	}
	session.getTransaction().commit();
    }
}
