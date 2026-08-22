package se.lexicon.dao;

import se.lexicon.model.Classroom;

import java.util.List;
import java.util.Optional;

public interface ClassroomDao {

    Classroom save(Classroom classroom);

    Optional<Classroom> findById(int id);

    List<Classroom> findAll();
}