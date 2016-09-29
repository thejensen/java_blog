import org.sql2o.*;
import java.util.List;

public class Comment {
  private String content;
  private int id;
  private int post_id;

  public Comment(String content, int post_id) {
    this.content = content;
    this.post_id = post_id;
    this.id = id;
  }

  public String getContent(){
    return content;
  }

  public int getId() {
    return id;
  }

  public int getPostId() {
    return post_id;
  }

  @Override
  public boolean equals(Object otherComment) {
    if(!(otherComment instanceof Comment)) {
      return false;
    } else {
      Comment newComment = (Comment) otherComment;
      return this.getContent().equals(newComment.getContent()) && this.getPostId() == newComment.getPostId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO comments (content, post_id) VALUES (:content, :post_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("content", this.content)
        .addParameter("post_id", this.post_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Comment> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments";
     return con.createQuery(sql).executeAndFetch(Comment.class);
    }
  }

  public void updateContent(String content) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE comments SET content=:content WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("content", content)
        .executeUpdate();
    }
  }

  public static Comment fetch(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments WHERE id=:id;";
      Comment comment = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Comment.class);
      return comment;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM comments WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  // public void addTag(Tag tag) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO posts_tags (post_id, tag_id) VALUES (:post_id, :tag_id)";
  //     con.createQuery(sql)
  //     .addParameter("post_id", this.getId())
  //     .addParameter("tag_id", tag.getId())
  //     .executeUpdate();
  //   }
  // }
  //
  // public List<Tag> getTags() {
  //   try(Connection con = DB.sql2o.open()) {
  //     // selects tagIds from the join table where the post id is 0 for testing, but increments up in our real database.
  //     String joinQuery = "SELECT tag_id FROM posts_tags WHERE post_id=:post_id";
  //     // assigns tagIds to an INTEGER list because we already have an integer class built into java we can use, don't need nothing special.
  //     List<Integer> tagIds = con.createQuery(joinQuery)
  //       .addParameter("post_id", this.getId())
  //       // Integer.class is baller here because we're just getting numbers, not whole objects, so.
  //       .executeAndFetch(Integer.class);
  //
  //     // declares an empty arraylist for the tags to get all up in
  //     List<Tag> tags = new ArrayList<Tag>();
  //
  //     // look! A foreach loop that loops through the tags table where tagId matches in the list we just generated. Also, note the immediate foresight to name the tags arraylist variable tagIds above, so we can loop it.
  //     for (Integer tagId : tagIds) {
  //       // we make the tagQuery its own var to pass into the query, so we can assign the whole query to another var. Pretty sure this is for readability.
  //       String tagQuery = "SELECT * FROM tags WHERE id = :tagId";
  //       // generates ONE object at a time using the sql+java.class magic in executeAndFetchFirst(Tag.class)
  //       Tag tag = con.createQuery(tagQuery)
  //         .addParameter("tagId", tagId)
  //         .executeAndFetchFirst(Tag.class);
  //       // adds one tag at a time to the tags list we declared outside the looop.
  //       tags.add(tag);
  //     }
  //     // returns the arraylist of tag objects that were added one by one in the foreach loop through the tags table using the ids we retrieved for the tags in the join table using the post ids.
  //     return tags;
  //   }
  // }

}
