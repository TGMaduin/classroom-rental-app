package se.lexicon.dao;

import java.util.List;
import java.util.Optional;

import se.lexicon.model.Booking;

public interface BookingDao {

    Booking save(Booking booking);

    Optional<Booking> findById(int id);

    List<Booking> findByCustomerId(int customerId);

    List<Booking> findByClassroomId(int classroomId);

    List<Booking> findUpcoming();
}
