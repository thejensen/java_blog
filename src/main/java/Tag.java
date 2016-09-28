import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Tag {
  private String theme;
  private int id;

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
    String sql = "SELECT * FROM tags";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }
  
//
//   public static Tag find(int id) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "SELECT * FROM persons where id=:id";
//       Tag person = con.createQuery(sql)
//         .addParameter("id", id)
//         .executeAndFetchFirst(Tag.class);
//       return person;
//     }
//   }
//
//   public List<Object> getMonsters() {
//     List<Object> allMonsters = new ArrayList<Object>();
//
//     try(Connection con = DB.sql2o.open()) {
//       String sqlFire = "SELECT * FROM monsters WHERE personId=:id AND type='fire';";
//       List<FireMonster> fireMonsters = con.createQuery(sqlFire)
//         .addParameter("id", this.id)
//         .throwOnMappingFailure(false)
//         .executeAndFetch(FireMonster.class);
//         allMonsters.addAll(fireMonsters);
//
//       String sqlWater = "SELECT * FROM monsters WHERE personId=:id AND type='water';";
//       List<WaterMonster> waterMonsters = con.createQuery(sqlWater)
//         .addParameter("id", this.id)
//         .throwOnMappingFailure(false)
//         .executeAndFetch(WaterMonster.class);
//         allMonsters.addAll(waterMonsters);
//       }
//       return allMonsters;
//     }
//
//
//   public List<Community> getCommunities() {
//     try(Connection con = DB.sql2o.open()){
//       String joinQuery = "SELECT community_id FROM communities_persons WHERE person_id = :person_id";
//       List<Integer> communityIds = con.createQuery(joinQuery)
//         .addParameter("person_id", this.getId())
//         .executeAndFetch(Integer.class);
//
//       List<Community> communities = new ArrayList<Community>();
//
//       for (Integer communityId : communityIds) {
//         String communityQuery = "SELECT * FROM communities WHERE id = :communityId";
//         Community community = con.createQuery(communityQuery)
//           .addParameter("communityId", communityId)
//           .executeAndFetchFirst(Community.class);
//         communities.add(community);
//       }
//       return communities;
//     }
//   }
//
//   public void leaveCommunity(Community community) {
//     try(Connection con = DB.sql2o.open()){
//       String joinRemovalQuery = "DELETE FROM communities_persons WHERE community_id = :communityId AND person_id = :personId;";
//         con.createQuery(joinRemovalQuery)
//           .addParameter("communityId", community.getId())
//           .addParameter("personId", this.getId())
//           .executeUpdate();
//     }
//   }
//
//
//
//
}
