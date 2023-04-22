package task5.ex_002_select_where;


import task5.ex_002_select_where.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class AuthorHelper {

    private final SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList() {
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();

        // этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder(); // не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)

        Selection[] selections = { root.get("id"), root.get("name") };

        criteriaQuery.select(criteriaBuilder.construct(Author.class, selections))
                .where(criteriaBuilder.like(root.get("name"), "%an%")); // like = "похоже на"

        // SQL-эквивалент построенного запроса
        // SELECT id, name FROM author
        // WHERE name LIKE '%an%';

        // этап выполнения запроса
        Query query = session.createQuery(criteriaQuery);
        List<Author> authorList = query.getResultList();

        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id); // получение объекта по id
        session.close();
        return author;
    }

    public Author addAuthor(Author author) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(author); // сгенерит ID и вставит в объект

        session.getTransaction().commit();

        session.close();


        return author;
    }

    //  Homework Task 5

    public List<Author> searchAuthorsBySeatchTerm(String searchTerm){
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();

        Root<Author> rt = cq.from(Author.class);
        Selection[] selections = {rt.get("id"), rt.get("name")};

        cq.select(cb.construct(Author.class, selections))
                .where(cb.like(rt.get("name"), "%" + searchTerm + "%"));

        Query query= session.createQuery(cq);
        List<Author> authorsList = query.getResultList();

        return  authorsList;
    }
}
