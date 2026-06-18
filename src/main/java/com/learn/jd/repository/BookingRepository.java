package com.learn.jd.repository;

import com.learn.jd.entity.Booking;
import com.learn.jd.entity.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByStatus(BookingStatus status);

    @Query("{ 'room.id': ?0, 'status': { $in: ?3 }, 'checkInDate': { $lt: ?2 }, 'checkOutDate': { $gt: ?1 } }")
    List<Booking> findOverlappingBookings(String roomId,
                                          LocalDate checkInDate,
                                          LocalDate checkOutDate,
                                          Collection<BookingStatus> statuses);
}
