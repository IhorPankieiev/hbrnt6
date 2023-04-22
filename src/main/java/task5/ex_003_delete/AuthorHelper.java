package task5.ex_003_delete;


import task5.ex_003_delete.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.*;
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
        System.out.println("\nReading all authors...");
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder(); // не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)

        // select all columns
        criteriaQuery.select(root);

        // select particular columns
//        Selection[] selections = { root.get("id"), root.get("name") };
//        criteriaQuery.select(criteriaBuilder.construct(Author.class, selections));

        // этап выполнения запроса
        Query query = session.createQuery(criteriaQuery);

        List<Author> authorList = query.getResultList();

        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Author author = session.get(Author.class, id); // получение объекта по id
        session.getTransaction().commit();
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

    public void readAndDeleteById(long id) {
        System.out.println("\nDeleting author by id...");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Author author = session.get(Author.class, id); // вычитка автора, подлежащего удалению
        if (author != null) {
            session.delete(author); // удаление автора
        }

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Author author) {
        System.out.println("\nDeleting author...");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (author != null) {
            session.delete(author); // удаление автора
        }

        session.getTransaction().commit();
        session.close();
    }

    public void deleteByNameUsingLikePattern() {
        System.out.println("\nDeleting by 'name' field using LIKE pattern...");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<Author> criteriaDelete = criteriaBuilder.createCriteriaDelete(Author.class);

        Root<Author> root = criteriaDelete.from(Author.class); // первостепенный, корневой entity (в sql запросе - from)

        criteriaDelete.where(criteriaBuilder.like(root.get("name"), "%an%")); // like = "похоже на"

        //этап выполнения запроса
        Query query = session.createQuery(criteriaDelete);
        int deletedRecords = query.executeUpdate();

        session.getTransaction().commit();
        System.out.println("Deleted records: " + deletedRecords);
        session.close();
    }

    public void deleteUsingComplexCondition() {
        System.out.println("\nDeleting using complex condition...");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaDelete<Author> criteriaDelete = criteriaBuilder.createCriteriaDelete(Author.class);

        Root<Author> root = criteriaDelete.from(Author.class);

        criteriaDelete.where(criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.like(root.get("name"), "C%"),
                        criteriaBuilder.like(root.get("lastName"), "D%")
                ),
                criteriaBuilder.equal(root.get("name"), "Herman")
        ));


        //этап выполнения запроса
        Query query = session.createQuery(criteriaDelete);
        int deletedRecords = query.executeUpdate();

        session.getTransaction().commit();

        System.out.println("Deleted records: " + deletedRecords);

        session.close();
    }

    // Homework task 5. Not sure about this.
    // In the task it's asked to edit methods, but there's none with those names.
    // So I decided to create these methods.
    // I'm using the coach's examples

    public void createCriteria() {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery cq = cb.createQuery(Author.class);

        Root<Author> rt = cq.from(Author.class);

        List<Author> authorList = session.createQuery(cq).getResultList();

        session.getTransaction().commit();

        session.close();

        System.out.println(authorList.toString());
    }

    public void createCriteriaLogic(){
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery cq = cb.createQuery(Author.class);

        Root<Author> rt = cq.from(Author.class);

        cq.where(cb.equal(rt.get("name"), "Franz"));

        Query query = session.createQuery(cq);
        List<Author> resultList = query.getResultList();

        session.getTransaction().commit();
        session.close();

        System.out.println(resultList);
    }
}
