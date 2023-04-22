package ex_004_relations;


import ex_004_relations.entity.Author;
import ex_004_relations.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookHelper {

    private final SessionFactory sessionFactory;

    public BookHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    List<Book> getBookList() {
        System.out.println("\nStart of getting all books...");
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Book.class);

        Root<Author> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);

        // этап выполнения запроса
        Query query = session.createQuery(criteriaQuery);
        List<Book> bookList = query.getResultList();

        session.close();
        System.out.println("End of getting all books...\n");
        return bookList;

    }

    public void deleteById(long id){
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Book book = session.get(Book.class, id);

        session.delete(book);

        session.getTransaction().commit();

        session.close();
    }

    public void deleteByAuthor(Author author){
        long authorId = author.getId();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaDelete cd = cb.createCriteriaDelete(Book.class);

        Root<Book> rt = cd.from(Book.class);

        cd.where(cb.equal(rt.get("author").get("id"), authorId));

        Query query = session.createQuery(cd);
        query.executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

}
