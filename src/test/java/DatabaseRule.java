import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/blog", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      // String deletePostsQuery = "DELETE FROM posts *;";
      String deleteTagsQuery = "DELETE FROM tags *;";
      // String deleteCommentsQuery = "DELETE FROM comments *;";
      // con.createQuery(deletePostsQuery).executeUpdate();
      con.createQuery(deleteTagsQuery).executeUpdate();
      // con.createQuery(deleteCommentsQuery).executeUpdate();
    }
  }


}
