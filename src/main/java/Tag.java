import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Tag {
  private String theme;
  private int id;

  public static final int MAX_TAGS = 7;

  public Tag(String theme) {
    this.theme = theme;
  }

  public String getTheme() {
    return theme;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherTag){
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getTheme().equals(newTag.getTheme());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (theme) VALUES (:theme)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("theme", this.theme)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Tag> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags";
     return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  public void update(String theme) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET theme=:theme WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("theme", theme)
        .executeUpdate();
    }
  }

  public static Tag fetch(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id;";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tags WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static List<Post> getPosts() {
    List<Post> posts = new ArrayList<Post>();

    try(Connection con = DB.sql2o.open()) {
      String
    }
    return posts;
  }

}
