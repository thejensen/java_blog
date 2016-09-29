import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;


public class CommentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void comment_instantiatesCorrectly_true() {
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    assertEquals(true, testComment instanceof Comment);
  }

  @Test
  public void equals_returnsEqualIfContentAndPostidIsTheSame_true(){
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    Comment secondTestComment = new Comment("WOW.", testPost.getId());
    assertTrue(testComment.equals(secondTestComment));
  }

  @Test
  public void save_savesPostToDatabase() {
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    testComment.save();
    assertTrue(Comment.all().get(0).equals(testComment));
  }

  @Test
  public void all_returnsAllInstancesOfComment_true() {
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    testComment.save();
    Comment secondComment = new Comment("Wine", testPost.getId());
    secondComment.save();
    assertTrue(Comment.all().get(0).equals(testComment));
    assertTrue(Comment.all().get(1).equals(secondComment));
  }

  @Test
  public void update_updatesContentInDatabase_String() {
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    testComment.save();
    testComment.updateContent("UMMMM.");
    assertEquals("UMMMM.", Comment.fetch(testComment.getId()).getContent());
  }

  @Test
  public void fetch_retrievesCommentObjectFromDatabase_Comment() {
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    testComment.save();
    assertEquals(testComment, Comment.fetch(testComment.getId()));
  }

  @Test
  public void delete_deletesCommentsFromDatabaseWithoutDeletingAssociatedCommentss_true() {
    Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
    testPost.save();
    Comment testComment = new Comment("WOW.", testPost.getId());
    testComment.save();
    testComment.delete();
    assertEquals(null, Comment.fetch(testComment.getId()));
  }

  // @Test
  // public void addTag_addsTagToPost() {
  //   Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
  //   testPost.save();
  //   Comment testComment = new Comment("WOW.", testPost.getId());
  //   testComment.save();
  //   Tag testTag = new Tag("Hiking");
  //   testTag.save();
  //   testPost.addTag(testTag);
  //   Tag savedTag = testPost.getTags().get(0);
  //   assertTrue(testTag.equals(savedTag));
  // }
  //
  // @Test
  // public void getTags_returnsAllTags_List() {
  //   Post testPost = new Post("Bird skeleton", "It's feather light, and here's why.");
  //   testPost.save();
  //   Comment testComment = new Comment("WOW.", testPost.getId());
  //   testComment.save();
  //   Tag testTag = new Tag("Hiking");
  //   testTag.save();
  //   // adds post and tag to their join table
  //   testPost.addTag(testTag);
  //   // assigns the tags from the join table where the post id is whatever to a List
  //   List savedTags = testPost.getTags();
  //   // making sure the savedTags size is 1, so we know there's something in there.
  //   assertEquals(savedTags.size(), 1);
  // }

}
