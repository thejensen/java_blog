import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class TagTest {
  Tag testTag;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void instantiatesObject() {
    testTag = new Tag("Acheesements");
  }

  @Test
  public void post_instantiatesCorrectly_true() {
    assertEquals(true, testTag instanceof Tag);
  }

  @Test
  public void equals_returnsTrueIfThemeIsTheSame_true() {
    Tag otherTag = new Tag("Acheesements");
    assertTrue(testTag.equals(otherTag));
  }

  @Test
  public void save_savesTagToDatabase() {
    testTag.save();
    assertTrue(testTag.all().get(0).equals(testTag));
  }

  @Test
  public void all_returnsAllInstancesOfTag_true() {
    testTag.save();
    Tag secondTag = new Tag("Travel");
    secondTag.save();
    assertTrue(Tag.all().get(0).equals(testTag));
    assertTrue(Tag.all().get(1).equals(secondTag));
  }

  @Test
  public void update_updatesTagThemeInDatabase_String() {
    testTag.save();
    testTag.update("Spelunking");
    assertEquals("Spelunking", Tag.fetch(testTag.getId()).getTheme());
  }

  @Test
  public void fetch_retrievesTagObjectFromDatabase_Tag() {
    testTag.save();
    assertEquals(testTag, Tag.fetch(testTag.getId()));
  }

  // @Test
  // public void getPosts_returnsAllPostsWithTagId_true() {
  //   testTag.save();
  //
  // }

  @Test
  public void delete_deletesTagFromDatabaseWithoutDeletingAssociatedPosts_true() {
    testTag.save();
    testTag.delete();
    assertEquals(null, Tag.fetch(testTag.getId()));
  }

}
