package task2;

import task2.entity.Author;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList() {
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();

        Criteria cb = session.createCriteria(Author.class);// не использовать session.createCriteria, т.к. deprecated

        List<Author> authorList = cb.list();

        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = (Author) session.get(Author.class, id); // получение объекта по id
        return author;
    }

    public Author addAuthor(Author author) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(author);

        session.getTransaction().commit();

        session.close();

        return author;
    }

    public void updateAuthorById(long id, String newName) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Author author = (Author) session.get(Author.class, id);

        author.setName(newName);

        session.saveOrUpdate(author);

        session.getTransaction().commit();

        session.close();
    }

    public void updateLastName(){
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate cu = cb.createCriteriaUpdate(Author.class);
        Root<Author> rt = cu.from(Author.class);

        cu.set(rt.get("name"), "1");
        cu.where(cb.greaterThan(cb.length(rt.get("lastName")), 7));
        int rowsUpdated = session.createQuery(cu).executeUpdate();
        session.getTransaction().commit();

        session.close();
        System.out.println("Updates execute: " + rowsUpdated);
    }
}
