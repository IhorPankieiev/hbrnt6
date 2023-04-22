package task5.ex_002_select_where;


import task5.ex_002_select_where.entity.Author;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());
    private static AuthorHelper authorHelper = new AuthorHelper();

    public static void main(String[] args) {

        createAuthors();

//        authorHelper.searchAuthorsBySeatchTerm("an");

        System.out.println(authorHelper.searchAuthorsBySeatchTerm("an").toString());
    }

    private static void createAuthors() {
        Author[] authors = {
                new Author("Franz", "Kafka"),
                new Author("Charles", "Dickens"),
                new Author("Herman", "Melville"),
                new Author("Clive", "Lewis")
        };

        for (Author author : authors) {
            authorHelper.addAuthor(author);
        }
    }

    private static void readCertainAuthors() {
        List<Author> authorList = authorHelper.getAuthorList();

        for (Author author : authorList) {
            LOG.debug(author);
            System.out.println(author);
        }
    }

    private static void readSpecificAuthor() {
        Author author = authorHelper.getAuthorById(4);
        LOG.debug(author.getName());
        System.out.println(author.getName());
    }


}
