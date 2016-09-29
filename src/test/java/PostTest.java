import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class PostTest {
  Post testPost;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void instantiatesObject() {
    testPost = new Post("Cheese", "How do I love cheese? Let me count the ways.");
  }

  @Test
  public void post_instantiatesCorrectly_true() {
    assertEquals(true, testPost instanceof Post);
  }

  @Test
  public void equals_returnsTrueIfTitleIsTheSame_true() {
    Post otherPost = new Post("Cheese", "How do I love cheese? Let me count the ways.");
    assertTrue(testPost.equals(otherPost));
  }

  @Test
  public void save_savesPostToDatabase() {
    testPost.save();
    assertTrue(testPost.all().get(0).equals(testPost));
  }

  @Test
  public void all_returnsAllInstancesOfPost_true() {
    testPost.save();
    Post secondPost = new Post("Wine", "Wine is also good.");
    secondPost.save();
    assertTrue(Post.all().get(0).equals(testPost));
    assertTrue(Post.all().get(1).equals(secondPost));
  }

  @Test
  public void update_updatesPostThemeInDatabase_String() {
    testPost.save();
    testPost.updateTitle("Spelunking");
    assertEquals("Spelunking", Post.fetch(testPost.getId()).getTitle());
  }

  @Test
  public void fetch_retrievesPostObjectFromDatabase_Post() {
    testPost.save();
    assertEquals(testPost, Post.fetch(testPost.getId()));
  }

  @Test
  public void delete_deletesPostFromDatabaseWithoutDeletingAssociatedPosts_true() {
    testPost.save();
    testPost.delete();
    assertEquals(null, Post.fetch(testPost.getId()));
  }

  @Test
  public void addPerson_addsPersonToCommunity() {
    Community testCommunity = new Community("Fire enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    testCommunity.addPerson(testPerson);
    Person savedPerson = testCommunity.getPersons().get(0);
    assertTrue(testPerson.equals(savedPerson));
  }

  // @Test
  // public void getPersons_returnsAllPersons_List() {
  //   Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
  //   testCommunity.save();
  //   Person testPerson = new Person("Henry", "henry@henry.com");
  //   testPerson.save();
  //   testCommunity.addPerson(testPerson);
  //   List savedPersons = testCommunity.getPersons();
  //   assertEquals(savedPersons.size(), 1);
  // }


}
