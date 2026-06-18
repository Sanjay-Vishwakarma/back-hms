package com.learn.jd;

import com.learn.jd.dto.BookingRequest;
import com.learn.jd.dto.BookingResponse;
import com.learn.jd.entity.Guest;
import com.learn.jd.entity.Room;
import com.learn.jd.repository.GuestRepository;
import com.learn.jd.repository.RoomRepository;
import com.learn.jd.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LearnJenkinsDockerApplicationTests {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private GuestRepository guestRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void createsAndReadsBookings() {
		Room room = roomRepository.findAll().get(0);
		Guest guest = guestRepository.findAll().get(0);
		BookingRequest request = new BookingRequest();
		request.setRoomId(room.getId());
		request.setGuestId(guest.getId());
		request.setCheckInDate(LocalDate.now().plusDays(1));
		request.setCheckOutDate(LocalDate.now().plusDays(3));
		request.setNumberOfGuests(1);

		bookingService.create(request);

		List<BookingResponse> bookings = bookingService.findAll();

		assertThat(bookings).isNotEmpty();
		assertThat(bookings.get(0).getRoomNumber()).isNotBlank();
		assertThat(bookings.get(0).getGuestName()).isNotBlank();
	}

}
