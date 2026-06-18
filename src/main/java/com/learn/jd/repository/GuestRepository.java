package com.learn.jd.repository;

import com.learn.jd.entity.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GuestRepository extends MongoRepository<Guest, String> {

    Optional<Guest> findByEmail(String email);
}
