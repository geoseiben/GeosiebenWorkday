package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.ChatListProjection;
import com.geosieben.gsbworkday.dto.EncryptedChattingProjection;
import com.geosieben.gsbworkday.entity.Chatting;

import jakarta.transaction.Transactional;

public interface ChattingRepository extends JpaRepository<Chatting,Integer>{
@Query(value = "WITH LatestChats AS (" +
        "    SELECT c1.*, " +
        "           CASE WHEN c1.fromUser = :eid THEN c1.toUser ELSE c1.fromUser END AS otherUser " +
        "    FROM chatting c1 " +
        "    INNER JOIN (" +
        "        SELECT LEAST(fromUser, toUser) AS userA, " +
        "               GREATEST(fromUser, toUser) AS userB, " +
        "               MAX(createdAt) AS latestTime " +
        "        FROM chatting " +
        "        WHERE fromUser = :eid OR toUser = :eid " +  // <-- CRITICAL FIX: Only look at chats involving the logged-in user
        "        GROUP BY LEAST(fromUser, toUser), GREATEST(fromUser, toUser)" +
        "    ) c2 ON LEAST(c1.fromUser, c1.toUser) = c2.userA " +
        "        AND GREATEST(c1.fromUser, c1.toUser) = c2.userB " +
        "        AND c1.createdAt = c2.latestTime" +
        ") " +
        "SELECT lc.msgid AS id, " +
        "       lc.otherUser, " +
        "       e.firstname, " +
        "       i.image, " +
        "       lc.isFile, " +
        "       lc.message, " +
        "       COALESCE(c3.unreadCount, 0) AS unreadCount, " +
        "       TIMESTAMPDIFF(HOUR, lc.createdat, CURRENT_TIMESTAMP) AS hour, " +
        "       TIMESTAMPDIFF(MINUTE, lc.createdat, CURRENT_TIMESTAMP) AS minute, " +
        "       TIMESTAMPDIFF(DAY, lc.createdat, CURRENT_TIMESTAMP) AS day " +
        "FROM LatestChats lc " +
        "LEFT JOIN images i ON i.eid = lc.otherUser " +
        "INNER JOIN employee_basic_info e ON e.eid = lc.otherUser " +
        "LEFT JOIN (" +
        "    SELECT fromUser, COUNT(*) AS unreadCount " +
        "    FROM chatting " +
        "    WHERE toUser = :eid AND isRead = 0 " +
        "    GROUP BY fromUser" +
        ") c3 ON c3.fromUser = lc.otherUser " +
        "ORDER BY lc.createdat DESC", 
        nativeQuery = true)
    public List<ChatListProjection> getChatLists(@Param("eid") String eid);

    @Query(value = "select msgId, case when fromuser=:myid then 'me' else fromuser end as fromUser ,"+
        "case when touser=:myid then 'me' else touser end as toUser ,message,createdAt,isDelivered,isRead,isFile,fileName,filePath "+
        " FROM chatting where (fromuser=:myid and touser=:otherId) or (fromuser=:otherId and touser=:myid) order by msgid asc ",nativeQuery = true
    )
    public List<EncryptedChattingProjection> getChatHistory(@Param("myid") String myId,@Param("otherId") String otherId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE chatting set isRead=1,isDelivered=1 where fromuser=:fromuser and touser=:touser",nativeQuery = true)
    public void markRead(@Param("fromuser") String fromuser,@Param("touser") String touser);

    @Transactional
    @Modifying
    @Query(value = "UPDATE chatting set isDelivered=1  where   touser=:touser",nativeQuery = true)
    public void markDelivered(@Param("touser") String touser);

}
