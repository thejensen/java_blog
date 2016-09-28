import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;


public class PostTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void post_instantiatesCorrectly_true() {
    Post testPost = new Post("Cheese", "How do I love cheese? Let me count the ways.");
    assertEquals(true, testPost instanceof Post);
  }


}
