package task5.ex_003_delete;


import task5.ex_003_delete.entity.Author;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {
        AuthorHelper authorHelper = new AuthorHelper();

//        authorHelper.createCriteria();
        authorHelper.createCriteriaLogic();
    }
}
