package com.learn.jd.repository;

import com.learn.jd.entity.Room;
import com.learn.jd.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findByActiveTrue();

    List<Room> findByTypeAndActiveTrue(RoomType type);
}
