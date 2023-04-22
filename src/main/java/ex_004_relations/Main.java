package ex_004_relations;


import ex_004_relations.entity.Author;
import ex_004_relations.entity.Book;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {
    AuthorHelper ah = new AuthorHelper();
    BookHelper bh = new BookHelper();

    bh.deleteByAuthor(ah.getAuthorById(2));
    bh.deleteById(1);
    }
}
