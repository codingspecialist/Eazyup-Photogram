package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

	@Modifying
	@Query(value = "INSERT INTO likes(imageId, userId, createDate) VALUES(:imageId, :principalId, now())", nativeQuery = true)
	int mLikes(@Param("imageId") int imageId, @Param("principalId") int principalId);

	@Modifying
	@Query(value = "DELETE FROM likes WHERE imageId = :imageId AND userId = :principalId", nativeQuery = true)
	int mUnLikes(@Param("imageId") int imageId, @Param("principalId") int principalId);
}
