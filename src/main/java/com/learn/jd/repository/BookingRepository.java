package com.learn.jd.repository;

import com.learn.jd.entity.Booking;
import com.learn.jd.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStatus(BookingStatus status);

    @Query("select b from Booking b where b.room.id = :roomId and b.status in :statuses and b.checkInDate < :checkOutDate and b.checkOutDate > :checkInDate")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("checkInDate") LocalDate checkInDate,
                                          @Param("checkOutDate") LocalDate checkOutDate,
                                          @Param("statuses") Collection<BookingStatus> statuses);
}
