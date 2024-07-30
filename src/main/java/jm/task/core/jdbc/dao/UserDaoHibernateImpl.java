package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

  public UserDaoHibernateImpl() {
  }

  @Override
  public void createUsersTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users("
        + "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(45), "
        + "LASTNAME VARCHAR(45), AGE TINYINT, PRIMARY KEY (ID) )";
    Transaction transaction = null;
    try (Session session = Util.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.createSQLQuery(sql).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    Transaction transaction = null;
    try (Session session = Util.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.createSQLQuery(sql).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    Transaction transaction = null;
    User user = new User(name, lastName, age);
    try (Session session = Util.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(user);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void removeUserById(long id) {
    Transaction transaction = null;
    try (Session session = Util.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      User user = session.get(User.class, id);
      if (user != null) {
        session.delete(user);
        transaction.commit();
      }
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<User> getAllUsers() {
    try (Session session = Util.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      List<User> users = session.createQuery("from User", User.class).getResultList();
      for (User user : users) {
        System.out.println(user);
      }
      transaction.commit();
      return users;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void cleanUsersTable() {
    Transaction transaction = null;
    try (Session session = Util.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.createQuery("delete from User").executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }
}