package task2;

import task2.entity.Author;
import task2.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookHelper {

    private SessionFactory sessionFactory;

    public BookHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Book> getBooksList(){
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Book.class);
        Query query = session.createQuery(cq);
        List<Book> allBooks = query.getResultList();

        session.close();

        return allBooks;
    }

    public Book getById(long id){
        Session session = sessionFactory.openSession();

        Book book = session.get(Book.class, id);

        session.close();

        return book;
    }

    public Book addBook(Book book){
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(book);

        session.getTransaction().commit();

        session.close();

        return book;

    }

    public Book updateBookById(long id, String newName){
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Book book = session.get(Book.class, id);

        book.setName(newName);

        session.saveOrUpdate(book);

        session.getTransaction().commit();

        session.close();

        return book;
    }

    public String getAuthorByBookName(String name) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<String> query = builder.createQuery(String.class);

        Root<Book> bookRoot = query.from(Book.class);

        Join<Book, Author> authorJoin = bookRoot.join("author");

        query.select(authorJoin.get("name")).where(builder.equal(bookRoot.get("name"), name));

        String authorName = session.createQuery(query).getSingleResult();

        session.close();

        return authorName;
    }

}
