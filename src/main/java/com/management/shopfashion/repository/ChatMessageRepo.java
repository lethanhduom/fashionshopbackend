package com.management.shopfashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.management.shopfashion.entity.ChatMessage;
import com.management.shopfashion.entity.User;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Integer> {
	 @Query("SELECT cm FROM ChatMessage cm WHERE " +
	           "((cm.sender.id_user = :userId AND cm.receive.id_user = :id_admin) OR " +
	           "(cm.sender.id_user = :id_admin AND cm.receive.id_user = :userId)) AND " +
	           "cm.product.id_product = :productId ORDER BY cm.datetime ASC")
	    List<ChatMessage> findChatWithAdmin(@Param("id_admin") int id_admin,@Param("userId") int userId, @Param("productId") int productId);
	 @Query("SELECT DISTINCT CASE WHEN cm.sender.id_user != :id_admin THEN cm.sender.id_user ELSE cm.receive.id_user END " +
		       "FROM ChatMessage cm WHERE cm.sender.id_user = :id_admin OR cm.receive.id_user = :id_admin ")
		List<Integer> findUserIdsChattedWithAdmin(@Param("id_admin") int id_admin);
	 @Query(value = """
			    SELECT * FROM (
			        SELECT cm.*, 
			               CASE 
			                   WHEN cm.id_sender = :idAdmin THEN cm.id_receiver 
			                   ELSE cm.id_sender 
			               END AS user_id,
			               ROW_NUMBER() OVER (
			                   PARTITION BY 
			                       CASE 
			                           WHEN cm.id_sender = :idAdmin THEN cm.id_receiver 
			                           ELSE cm.id_sender 
			                       END,
			                       cm.id_product
			                   ORDER BY cm.date_time DESC
			               ) AS rn
			        FROM chat_message cm
			        WHERE cm.id_sender = :idAdmin OR cm.id_receiver = :idAdmin
			    ) sub
			    WHERE sub.rn = 1
			    """, nativeQuery = true)
			List<ChatMessage> findLatestMessagePerUserAndProductWithAdmin(@Param("idAdmin") int idAdmin);

	
}
