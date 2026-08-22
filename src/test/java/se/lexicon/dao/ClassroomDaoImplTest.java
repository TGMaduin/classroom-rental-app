package se.lexicon.dao;

import org.junit.jupiter.api.Test;
import se.lexicon.model.Classroom;
import se.lexicon.model.Equipment;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomDaoImplTest {

    private final ClassroomDao classroomDao = new ClassroomDaoImpl();

    @Test
    void shouldSaveClassroomAndReturnGeneratedId() {
        Classroom classroom = new Classroom(
                uniqueRoomName("Test Room"),
                25,
                true,
                EnumSet.of(
                        Equipment.PROJECTOR,
                        Equipment.WHITEBOARD
                )
        );

        Classroom savedClassroom = classroomDao.save(classroom);

        assertTrue(savedClassroom.getId() > 0);
        assertEquals(classroom.getName(), savedClassroom.getName());
        assertEquals(classroom.getCapacity(), savedClassroom.getCapacity());
        assertEquals(
                classroom.isDisabilityAccessible(),
                savedClassroom.isDisabilityAccessible()
        );
        assertEquals(
                classroom.getEquipment(),
                savedClassroom.getEquipment()
        );
    }

    @Test
    void shouldFindClassroomByIdIncludingEquipment() {
        Classroom classroom = new Classroom(
                uniqueRoomName("Find By Id Room"),
                30,
                true,
                EnumSet.of(
                        Equipment.PROJECTOR,
                        Equipment.WHITEBOARD
                )
        );

        Classroom savedClassroom = classroomDao.save(classroom);

        Optional<Classroom> result =
                classroomDao.findById(savedClassroom.getId());

        assertTrue(result.isPresent());

        Classroom foundClassroom = result.get();

        assertEquals(savedClassroom.getId(), foundClassroom.getId());
        assertEquals(classroom.getName(), foundClassroom.getName());
        assertEquals(classroom.getCapacity(), foundClassroom.getCapacity());
        assertEquals(
                classroom.isDisabilityAccessible(),
                foundClassroom.isDisabilityAccessible()
        );
        assertEquals(
                classroom.getEquipment(),
                foundClassroom.getEquipment()
        );
    }

    @Test
    void shouldReturnEmptyOptionalWhenClassroomDoesNotExist() {
        Optional<Classroom> result =
                classroomDao.findById(Integer.MAX_VALUE);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindAllClassroomsIncludingEquipment() {
        Classroom classroom = new Classroom(
                uniqueRoomName("Find All Room"),
                18,
                false,
                EnumSet.of(Equipment.WHITEBOARD)
        );

        Classroom savedClassroom = classroomDao.save(classroom);

        List<Classroom> classrooms = classroomDao.findAll();

        assertFalse(classrooms.isEmpty());

        Optional<Classroom> result = classrooms.stream()
                .filter(c -> c.getId() == savedClassroom.getId())
                .findFirst();

        assertTrue(result.isPresent());

        Classroom foundClassroom = result.get();

        assertEquals(classroom.getName(), foundClassroom.getName());
        assertEquals(classroom.getCapacity(), foundClassroom.getCapacity());
        assertEquals(
                classroom.isDisabilityAccessible(),
                foundClassroom.isDisabilityAccessible()
        );
        assertEquals(
                classroom.getEquipment(),
                foundClassroom.getEquipment()
        );
    }

    @Test
    void shouldUpdateClassroomIncludingEquipment() {

        Classroom classroom = new Classroom(
                uniqueRoomName("Update Room"),
                20,
                false,
                EnumSet.of(Equipment.WHITEBOARD)
        );

        Classroom savedClassroom = classroomDao.save(classroom);

        savedClassroom.setName(
                uniqueRoomName("Updated Room")
        );
        savedClassroom.setCapacity(40);
        savedClassroom.setDisabilityAccessible(true);
        savedClassroom.setEquipment(
                EnumSet.of(
                        Equipment.PROJECTOR,
                        Equipment.WHITEBOARD
                )
        );

        classroomDao.update(savedClassroom);

        Optional<Classroom> result =
                classroomDao.findById(savedClassroom.getId());

        assertTrue(result.isPresent());

        Classroom updatedClassroom = result.get();

        assertEquals(
                savedClassroom.getName(),
                updatedClassroom.getName()
        );

        assertEquals(
                savedClassroom.getCapacity(),
                updatedClassroom.getCapacity()
        );

        assertEquals(
                savedClassroom.isDisabilityAccessible(),
                updatedClassroom.isDisabilityAccessible()
        );

        assertEquals(
                savedClassroom.getEquipment(),
                updatedClassroom.getEquipment()
        );
    }

    @Test
    void shouldRemoveEquipmentWhenClassroomIsUpdated() {

        Classroom classroom = new Classroom(
                uniqueRoomName("Equipment Update Room"),
                25,
                true,
                EnumSet.of(
                        Equipment.PROJECTOR,
                        Equipment.WHITEBOARD
                )
        );

        Classroom savedClassroom = classroomDao.save(classroom);

        savedClassroom.setEquipment(
                EnumSet.of(Equipment.PROJECTOR)
        );

        classroomDao.update(savedClassroom);

        Classroom updatedClassroom =
                classroomDao.findById(savedClassroom.getId())
                        .orElseThrow();

        assertEquals(
                EnumSet.of(Equipment.PROJECTOR),
                updatedClassroom.getEquipment()
        );

        assertFalse(
                updatedClassroom.getEquipment()
                        .contains(Equipment.WHITEBOARD)
        );
    }

    private String uniqueRoomName(String prefix) {
        return prefix + " " + System.nanoTime();
    }
}