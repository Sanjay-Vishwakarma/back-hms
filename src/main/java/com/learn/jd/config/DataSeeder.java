package com.learn.jd.config;

import com.learn.jd.entity.Guest;
import com.learn.jd.entity.Room;
import com.learn.jd.entity.RoomType;
import com.learn.jd.repository.GuestRepository;
import com.learn.jd.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    public DataSeeder(RoomRepository roomRepository, GuestRepository guestRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public void run(String... args) {
        if (roomRepository.count() == 0) {
            roomRepository.saveAll(Arrays.asList(
                    new Room("101", RoomType.SINGLE, 1, new BigDecimal("1800.00")),
                    new Room("102", RoomType.DOUBLE, 2, new BigDecimal("2800.00")),
                    new Room("201", RoomType.DELUXE, 3, new BigDecimal("4200.00")),
                    new Room("301", RoomType.SUITE, 4, new BigDecimal("7000.00"))
            ));
        }

        if (guestRepository.count() == 0) {
            guestRepository.saveAll(Arrays.asList(
                    new Guest("Aarav Sharma", "aarav@example.com", "9876543210", "AADHAAR-1001"),
                    new Guest("Mira Kapoor", "mira@example.com", "9876501234", "PASSPORT-2002")
            ));
        }
    }
}
