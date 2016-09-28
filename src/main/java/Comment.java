import org.sql2o.*;

public class Comment {
  private String content;
  // private int id;

  public Comment(String content) {
    this.content = content;
    // this.postId = postId;
    // this.id = id;
  }

  public String getContent(){
    return content;
  }
  //
  // public int getId() {
  //   return id;
  // }
  //
  // public int getPostId() {
  //   return postId;
  // }


}
