import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

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
  public void addTag_addsTagToPost() {
    testPost.save();
    Tag testTag = new Tag("Hiking");
    testTag.save();
    testPost.addTag(testTag);
    Tag savedTag = testPost.getTags().get(0);
    assertTrue(testTag.equals(savedTag));
  }

  @Test
  public void getTags_returnsAllTags_List() {
    testPost.save();
    Tag testTag = new Tag("Hiking");
    testTag.save();
    // adds post and tag to their join table
    testPost.addTag(testTag);
    // assigns the tags from the join table where the post id is whatever to a List
    List savedTags = testPost.getTags();
    // making sure the savedTags size is 1, so we know there's something in there.
    assertEquals(savedTags.size(), 1);
  }


}
