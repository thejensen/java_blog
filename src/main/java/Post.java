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
      String sql = "INSERT INTO communities_tags (community_id, tag_id) VALUES (:community_id, :tag_id)";
      con.createQuery(sql)
      .addParameter("community_id", this.getId())
      .addParameter("tag_id", tag.getId())
      .executeUpdate();
    }
  }

  // public List<Tag> getTags() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT tag_id FROM communities_tags WHERE community_id = :community_id";
  //     List<Integer> tagIds = con.createQuery(joinQuery)
  //       .addParameter("community_id", this.getId())
  //       .executeAndFetch(Integer.class);
  //
  //     List<Tag> tags = new ArrayList<Tag>();
  //
  //     for (Integer tagId : tagIds) {
  //       String tagQuery = "SELECT * FROM tags WHERE id = :tagId";
  //       Tag tag = con.createQuery(tagQuery)
  //         .addParameter("tagId", tagId)
  //         .executeAndFetchFirst(Tag.class);
  //       tags.add(tag);
  //     }
  //     return tags;
  //   }
  // }

}
