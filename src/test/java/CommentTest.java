import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;


public class CommentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void comment_instantiatesCorrectly_true() {
    Comment testComment = new Comment("WOW.");
    assertEquals(true, testComment instanceof Comment);
  }

}
