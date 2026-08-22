package se.lexicon.dao;

import se.lexicon.model.BookingUser;

import java.util.List;
import java.util.Optional;

public interface BookingUserDao {

    BookingUser save(BookingUser bookingUser);

    Optional<BookingUser> findById(int id);

    List<BookingUser> findByCustomerId(int customerId);
}