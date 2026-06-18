package com.learn.jd.repository;

import com.learn.jd.entity.Room;
import com.learn.jd.entity.RoomType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findByActiveTrue();

    List<Room> findByTypeAndActiveTrue(RoomType type);
}
