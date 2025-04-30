package com.bit.mini.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bit.mini.dto.AdDto;
import com.bit.mini.dto.CommunityPostDto;
import com.bit.mini.dto.UserDto;

@Repository
public class AdminDao {

	@Autowired
    JdbcTemplate template;
	
	
	public int getTotalUserCount() {
		String sql = "SELECT COUNT(*) FROM users";
        return template.queryForObject(sql, Integer.class);
	}

	
	
	public List<UserDto> getUserList() {
	    String sql = "SELECT id, username, name, email, gender FROM users";
	    List<UserDto> userList = template.query(sql, new BeanPropertyRowMapper<>(UserDto.class));
	    
	    return userList;
	}



	
	
	
	public UserDto getUserById(int id) {
	    String sql = "SELECT * FROM users WHERE id = ?";
	    System.out.println("Dao에 왔다 조회할 게시글 ID: " + id);
	    try {
	        return template.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), id);
	    } catch (EmptyResultDataAccessException e) {
	        System.err.println("ID " + id + "에 해당하는 사용자가 존재하지 않습니다.");
	        return null; 
	    }
	}


	
	
	
	
	public void deleteMember(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        
        
        int rowsAffected = template.update(sql, username);
        
    }

	
	public void deletePost(String postId) {
	    String sql = "DELETE FROM posts WHERE id = ?";
	    template.update(sql, postId); 
	}



	/**
     * 년/월 기준으로 게시글 필터링
     * @param year - 년도
     * @param month - 월
     * @return 게시글 리스트
     */
    public List<CommunityPostDto> getPostsByYearAndMonth(int year, int month) {
        String sql = "SELECT p.id, p.title, p.content, p.imagePath, p.user_id, p.hit, p.created_at, p.updated_at, " +
                     "       u.username AS author " +
                     "FROM posts p " +
                     "JOIN users u ON p.user_id = u.id " +
                     "WHERE YEAR(p.created_at) = ? AND MONTH(p.created_at) = ? " +
                     "ORDER BY p.created_at DESC";

        return template.query(sql, new Object[]{year, month}, (rs, rowNum) -> {
            CommunityPostDto post = new CommunityPostDto();
            post.setId(rs.getInt("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setImagePath(rs.getString("imagePath"));
            post.setUserId(rs.getInt("user_id"));
            post.setHit(rs.getInt("hit"));
            post.setCreated_at(rs.getTimestamp("created_at"));
            post.setUpdatedAt(rs.getTimestamp("updated_at"));
            post.setAuthor(rs.getString("author")); // 작성자
            return post;
        });
    }




	/**
     * 주별 게시글 수를 반환
     * @param year - 년도
     * @param month - 월
     * @return 주별 게시글 수 리스트
     */
    public List<Map<String, Object>> getWeeklyPostStats(int year, int month) {
        String sql = "SELECT " +
                     "  CEIL(DAYOFMONTH(created_at) / 7) AS week, " +
                     "  COUNT(*) AS post_count " +
                     "FROM posts " +
                     "WHERE YEAR(created_at) = ? AND MONTH(created_at) = ? " +
                     "GROUP BY week " +
                     "ORDER BY week";

        return template.query(sql, new Object[]{year, month}, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("week", rs.getInt("week")); // 몇 번째 주
            map.put("post_count", rs.getInt("post_count")); // 해당 주의 게시글 수
            return map;
        });
    }

	
    
 // 공통 RowMapper
    
    private final RowMapper<AdDto> adRowMapper = (rs, rowNum) -> {
        AdDto ad = new AdDto();
        ad.setId(rs.getInt("id"));
        ad.setImageUrl(rs.getString("image_url"));
        ad.setLinkUrl(rs.getString("link_url"));
        //ad.setActive(rs.getBoolean("is_active"));
        
        ad.setActive(rs.getInt("is_active") == 1); 
        
        System.out.println("광고 ID: " + ad.getId() + ", isActive(DB 값): " + rs.getInt("is_active") + ", 변환된 값: " + ad.getIsActive());
        
        return ad;
    };


    
    
    
    public List<AdDto> getAds(boolean onlyActive) {
        String query = "SELECT id, image_url, link_url, is_active FROM ads";
        if (onlyActive) {
            query += " WHERE is_active = 1";
        }
        return template.query(query, adRowMapper); 
    }

    
    
    

    public AdDto getRandomAd() {
        List<AdDto> activeAds = getAds(true); // 활성화된 광고만 가져오기
        if (!activeAds.isEmpty()) {
            Collections.shuffle(activeAds); // 광고 무작위 섞기
            return activeAds.get(0); // 첫 번째 광고 반환
        }
        return null; // 활성화된 광고가 없으면 null 반환
    }

    
    //광고 추가
    public void addAd(AdDto ad) {
        String query = "INSERT INTO ads (image_url, link_url, is_active) VALUES (?, ?, ?)";
        template.update(query, ad.getImageUrl(), ad.getLinkUrl(), ad.getIsActive() ? 1 : 0);
    }
    
    //광고 삭제
    public void deleteAd(int id) {
        String query = "DELETE FROM ads WHERE id = ?";
        template.update(query, id);
    }

	
	public List<AdDto> getActiveAds() {
        String query = "SELECT * FROM ads WHERE is_active = 1";
        //return template.query(query, new BeanPropertyRowMapper<>(AdDto.class));
        return template.query(query, adRowMapper);
    }



	
}


