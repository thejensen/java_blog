import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Post {
  private String title;
  private String body;
  private int id;

  public Post(String title, String body) {
    this.title = title;
    this.body = body;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherPost){
    if (!(otherPost instanceof Post)) {
      return false;
    } else {
      Post newPost = (Post) otherPost;
      return this.getTitle().equals(newPost.getTitle()) && this.getBody().equals(newPost.getBody());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO posts (title, body) VALUES (:title, :body)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("body", this.body)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Post> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts";
     return con.createQuery(sql).executeAndFetch(Post.class);
    }
  }

  public void updateTitle(String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE posts SET title=:title WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("title", title)
        .executeUpdate();
    }
  }

  public static Post fetch(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts WHERE id=:id;";
      Post post = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Post.class);
      return post;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM posts WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addTag(Tag tag) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO posts_tags (post_id, tag_id) VALUES (:post_id, :tag_id)";
      con.createQuery(sql)
      .addParameter("post_id", this.getId())
      .addParameter("tag_id", tag.getId())
      .executeUpdate();
    }
  }

  public List<Tag> getTags() {
    try(Connection con = DB.sql2o.open()) {
      // selects tagIds from the join table where the post id is 0 for testing, but increments up in our real database.
      String joinQuery = "SELECT tag_id FROM posts_tags WHERE post_id=:post_id";
      // assigns tagIds to an INTEGER list because we already have an integer class built into java we can use, don't need nothing special.
      List<Integer> tagIds = con.createQuery(joinQuery)
        .addParameter("post_id", this.getId())
        // Integer.class is baller here because we're just getting numbers, not whole objects, so.
        .executeAndFetch(Integer.class);

      // declares an empty arraylist for the tags to get all up in
      List<Tag> tags = new ArrayList<Tag>();

      // look! A foreach loop that loops through the tags table where tagId matches in the list we just generated. Also, note the immediate foresight to name the tags arraylist variable tagIds above, so we can loop it.
      for (Integer tagId : tagIds) {
        // we make the tagQuery its own var to pass into the query, so we can assign the whole query to another var. Pretty sure this is for readability.
        String tagQuery = "SELECT * FROM tags WHERE id = :tagId";
        // generates ONE object at a time using the sql+java.class magic in executeAndFetchFirst(Tag.class)
        Tag tag = con.createQuery(tagQuery)
          .addParameter("tagId", tagId)
          .executeAndFetchFirst(Tag.class);
        // adds one tag at a time to the tags list we declared outside the looop.
        tags.add(tag);
      }
      // returns the arraylist of tag objects that were added one by one in the foreach loop through the tags table using the ids we retrieved for the tags in the join table using the post ids.
      return tags;
    }
  }

}
