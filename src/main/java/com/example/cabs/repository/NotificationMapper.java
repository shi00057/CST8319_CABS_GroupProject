package com.example.cabs.repository;

import com.example.cabs.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    Integer notificationsCountUnread(@Param("p_UserId") Integer userId);
    List<NotificationDto> notificationsListForUser(@Param("p_UserId") Integer userId,
                                                   @Param("p_OnlyUnread") Boolean onlyUnread,
                                                   @Param("p_Top") Integer top);
    int notificationsMarkRead(@Param("p_UserId") Integer userId, @Param("p_NotificationId") Long notificationId);
    int notificationsMarkAll(@Param("p_UserId") Integer userId);
    int notificationsDelete(@Param("p_UserId") Integer userId, @Param("p_NotificationId") Long notificationId);
    int notifyCreate(@Param("p_Type") String type);
}
