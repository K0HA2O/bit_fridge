package com.bit.mini.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bit.mini.dto.CommunityPostDto;
import com.bit.mini.dto.UserDto;



@Repository
public class CommunityDao {

	@Autowired
	private JdbcTemplate template;

	


	public List<CommunityPostDto> getTopPosts(int page, int size) {
	    // page가 1보다 작은 경우 1로 설정하여 음수 offset 방지
	    page = Math.max(page, 1);
	    int offset = (page - 1) * size;

	    String query = "SELECT p.id, p.title, p.content, u.name AS author, p.imagePath, " +
	                   "p.user_id, p.hit, p.created_at, p.updated_at, " +
	                   "(SELECT COUNT(*) FROM likes l WHERE l.post_id = p.id) AS likeCount " + // COUNT(l.id) 서브쿼리 처리
	                   "FROM posts p " +
	                   "LEFT JOIN users u ON p.user_id = u.id " + // 사용자 정보 조인
	                   "ORDER BY likeCount DESC " +
	                   "LIMIT ? OFFSET ?"; // LIMIT과 OFFSET 올바르게 사용

	    return template.query(query, new BeanPropertyRowMapper<>(CommunityPostDto.class), size, offset);
	}

	
	
	
	
	public int getTotalPostCount() {
	    String query = "SELECT COUNT(*) FROM posts";
	    return template.queryForObject(query, Integer.class);
	}

	
	
	
	//커뮤니티에 상위글 3개 가져오는 메소드
	public List<CommunityPostDto> getTopThreePosts() {
	    String query = "SELECT p.id, p.title, p.content, u.name AS author, p.imagePath, " +
	                   "p.user_id, p.hit, p.created_at, p.updated_at, COUNT(l.id) AS likeCount " +
	                   "FROM posts p " +
	                   "LEFT JOIN likes l ON p.id = l.post_id " +
	                   "LEFT JOIN users u ON p.user_id = u.id " +
	                   "GROUP BY p.id " +
	                   "ORDER BY likeCount DESC " +
	                   "LIMIT 3"; 
	    return template.query(query, new BeanPropertyRowMapper<>(CommunityPostDto.class));
	}



    
	// 게시글을 가장 많이 작성한 사용자 가져오는 메소드
		public UserDto getMostActiveUser() {
			String query = "SELECT u.id, u.username, u.gender, u.name " + "FROM users u "
					+ "JOIN posts p ON u.id = p.user_id " + "GROUP BY u.id " + "ORDER BY COUNT(p.id) DESC " + "LIMIT 1";

			return template.queryForObject(query,
					(rs, rowNum) -> new UserDto(
							rs.getInt("id"), 
							rs.getString("username"), 
							rs.getString("name"), // 비밀번호는 않음
							null, // 이메일도 가져오지 않음
							null, // 이름도 가져오지 않음
							null, // 생일도 가져오지 않음
							UserDto.Gender.valueOf(rs.getString("gender")), // 성별을 Enum으로 변환
							null, // 건강정보 제외
							null // 알레르기 정보 제외
					));
		}
    
	// 게시글 저장하는 메소드
		public void insertPost(String title, String content, int userId, String imagePath) {
		    String query = "INSERT INTO posts (title, content, user_id, imagePath, created_at) VALUES (?, ?, ?, ?, NOW())";
		    template.update(query, title, content, userId, imagePath);
		}
		
		

		
		
	//게시글 조회수 오르는 메소드
		public void upHit(int postId) {
			System.out.println("조회수 증가 - postId: " + postId);
			String query = "UPDATE posts SET hit = hit + 1 WHERE id = ?";
		    template.update(query, postId);		
		}
		
	
	
	
	public CommunityPostDto getPostDetail(int postId) {
	    String query = "SELECT p.id, p.title, p.content, p.imagePath, p.user_id, p.hit, " +
	                   "p.created_at, p.updated_at, u.name AS author, " +
	                   "COUNT(l.id) AS likeCount " +
	                   "FROM posts p " +
	                   "LEFT JOIN likes l ON p.id = l.post_id " +
	                   "LEFT JOIN users u ON p.user_id = u.id " +
	                   "WHERE p.id = ? " +
	                   "GROUP BY p.id";
	    
	    System.out.println("Dao에 왔다 조회할 게시글 ID: " + postId);
	    
	    
	    return template.queryForObject(query, new Object[]{postId}, new BeanPropertyRowMapper<>(CommunityPostDto.class));
	}

	
	
	
	
	// 게시글 수정하는 메소드
	public void updatePost(int postId, String title, String content, String imagePath) {
		String query = "UPDATE posts SET title = ?, content = ?, imagePath = ?, updated_at = NOW() WHERE id = ?";
		template.update(query, title, content, imagePath, postId);
	}



	// 게시글 삭제 메소드 (posts 테이블)
	public void deletePostById(int postId) {
		String query = "DELETE FROM posts WHERE id = ?";
		template.update(query, postId);
	}
    
    // 좋아요 삭제 메소드 (likes 테이블)
 	public void removeLike(int postId, int userId) {
 	    String query = "DELETE FROM likes WHERE post_id = ? AND user_id = ?";
 	   System.out.println("Dao에 왔다 removeLike");
 	    template.update(query, postId, userId);
 	}

 	 public void increaseLike(int postId, int userId) {
         String query = "INSERT INTO likes (post_id, user_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE post_id = post_id";
         template.update(query, postId, userId);
     }

	 
 	public void decreaseLike(int postId, int userId) {
    	System.out.println("Dao에 왔다 decreaseLike");
        String query = "DELETE FROM likes WHERE post_id = ? AND user_id = ?";
        
        template.update(query, postId, userId);
    }


 	public int getLikeCount(int postId) {
        String query = "SELECT COUNT(*) FROM likes WHERE post_id = ?";
        return template.queryForObject(query, Integer.class, postId);
    }



	
	public boolean hasUserLiked(int postId, int userId) {
	    System.out.println("Dao에 왔다 hasUserLiked");
	    String query = "SELECT COUNT(*) FROM likes WHERE post_id = ? AND user_id = ?";
	    Integer count = template.queryForObject(query, Integer.class, postId, userId);
	    return count != null && count > 0;
	}





	
	public CommunityPostDto clickContent(int postId) {
	    String query = "SELECT p.id, p.title, p.content, p.imagePath, p.user_id, p.hit, " +
	                   "p.created_at, p.updated_at, u.name AS author, " +
	                   "COUNT(l.id) AS likeCount " +
	                   "FROM posts p " +
	                   "LEFT JOIN likes l ON p.id = l.post_id " +
	                   "LEFT JOIN users u ON p.user_id = u.id " +
	                   "WHERE p.id = ? " +
	                   "GROUP BY p.id";

	    return template.queryForObject(
	        query,
	        new Object[]{postId},
	        new BeanPropertyRowMapper<>(CommunityPostDto.class)
	    );
	}


	
	
	public List<CommunityPostDto> getPostsByUser(String username) {
	    String query = "SELECT p.id, p.title, p.content, p.imagePath, p.user_id, p.hit, " +
	                   "p.created_at, p.updated_at, u.name AS author, " +
	                   "COUNT(l.id) AS likeCount " +
	                   "FROM posts p " +
	                   "LEFT JOIN likes l ON p.id = l.post_id " +
	                   "LEFT JOIN users u ON p.user_id = u.id " +
	                   "WHERE u.username = ? " +
	                   "GROUP BY p.id";
	    return template.query(query,
	        new Object[]{username},
	        new BeanPropertyRowMapper<>(CommunityPostDto.class)
	    );
	}

	
	



	

	

}
