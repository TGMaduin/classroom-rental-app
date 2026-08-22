package se.lexicon.dao;

import se.lexicon.database.DatabaseConnection;
import se.lexicon.model.Classroom;
import se.lexicon.model.Equipment;

import java.sql.*;
import java.util.*;

public class ClassroomDaoImpl implements ClassroomDao{

    @Override
    public Classroom save(Classroom classroom) {

        String classroomSql = """
            INSERT INTO classroom
            (name, capacity, disability_accessible)
            VALUES (?, ?, ?)
            """;

        String equipmentSql = """
            INSERT INTO classroom_equipment
            (classroom_id, equipment_type)
            VALUES (?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (
                    PreparedStatement classroomStatement = connection.prepareStatement(
                            classroomSql,
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                classroomStatement.setString(1, classroom.getName());
                classroomStatement.setInt(2, classroom.getCapacity());
                classroomStatement.setBoolean(
                        3,
                        classroom.isDisabilityAccessible()
                );

                classroomStatement.executeUpdate();

                try (ResultSet generatedKeys = classroomStatement.getGeneratedKeys()) {

                    if (!generatedKeys.next()) {
                        connection.rollback();
                        throw new RuntimeException(
                                "Failed to retrieve generated classroom id"
                        );
                    }

                    int classroomId = generatedKeys.getInt(1);

                    try (
                            PreparedStatement equipmentStatement =
                                    connection.prepareStatement(equipmentSql)
                    ) {
                        for (Equipment equipment : classroom.getEquipment()) {
                            equipmentStatement.setInt(1, classroomId);
                            equipmentStatement.setString(2, equipment.name());
                            equipmentStatement.executeUpdate();
                        }
                    }

                    connection.commit();

                    return new Classroom(
                            classroomId,
                            classroom.getName(),
                            classroom.getCapacity(),
                            classroom.isDisabilityAccessible(),
                            classroom.getEquipment()
                    );
                }

            } catch (SQLException | RuntimeException e) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }

                throw new RuntimeException("Failed to save classroom", e);

            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // Connection will be closed by try-with-resources.
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    @Override
    public Optional<Classroom> findById(int id) {

        String sql = """
            SELECT *
            FROM classroom
            WHERE id = ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Set<Equipment> equipment =
                            getEquipmentForClassroom(connection, id);

                    return Optional.of(
                            mapClassroom(resultSet, equipment)
                    );
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to retrieve classroom",
                    e
            );
        }
    }

    @Override
    public List<Classroom> findAll() {

        String sql = """
            SELECT *
            FROM classroom
            """;

        List<Classroom> classrooms = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int classroomId = resultSet.getInt("id");

                Set<Equipment> equipment =
                        getEquipmentForClassroom(connection, classroomId);

                classrooms.add(
                        mapClassroom(resultSet, equipment)
                );
            }

            return classrooms;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to retrieve classrooms",
                    e
            );
        }
    }

    @Override
    public void update(Classroom classroom) {

        String classroomSql = """
            UPDATE classroom
            SET name = ?,
                capacity = ?,
                disability_accessible = ?
            WHERE id = ?
            """;

        String deleteEquipmentSql = """
            DELETE FROM classroom_equipment
            WHERE classroom_id = ?
            """;

        String insertEquipmentSql = """
            INSERT INTO classroom_equipment
            (classroom_id, equipment_type)
            VALUES (?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                try (PreparedStatement classroomStatement =
                             connection.prepareStatement(classroomSql)) {

                    classroomStatement.setString(1, classroom.getName());
                    classroomStatement.setInt(2, classroom.getCapacity());
                    classroomStatement.setBoolean(
                            3,
                            classroom.isDisabilityAccessible()
                    );
                    classroomStatement.setInt(4, classroom.getId());

                    classroomStatement.executeUpdate();
                }

                try (PreparedStatement deleteEquipmentStatement =
                             connection.prepareStatement(deleteEquipmentSql)) {

                    deleteEquipmentStatement.setInt(
                            1,
                            classroom.getId()
                    );

                    deleteEquipmentStatement.executeUpdate();
                }

                try (PreparedStatement insertEquipmentStatement =
                             connection.prepareStatement(insertEquipmentSql)) {

                    for (Equipment equipment : classroom.getEquipment()) {
                        insertEquipmentStatement.setInt(
                                1,
                                classroom.getId()
                        );
                        insertEquipmentStatement.setString(
                                2,
                                equipment.name()
                        );

                        insertEquipmentStatement.executeUpdate();
                    }
                }

                connection.commit();

            } catch (SQLException | RuntimeException e) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }

                throw new RuntimeException(
                        "Failed to update classroom",
                        e
                );

            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // Connection will be closed by try-with-resources.
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to connect to database",
                    e
            );
        }
    }

    private Set<Equipment> getEquipmentForClassroom(
            Connection connection,
            int classroomId
    ) throws SQLException {

        String sql = """
            SELECT equipment_type
            FROM classroom_equipment
            WHERE classroom_id = ?
            """;

        Set<Equipment> equipment = EnumSet.noneOf(Equipment.class);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, classroomId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    equipment.add(
                            Equipment.valueOf(
                                    resultSet.getString("equipment_type")
                            )
                    );
                }
            }
        }

        return equipment;
    }

    private Classroom mapClassroom(
            ResultSet resultSet,
            Set<Equipment> equipment
    ) throws SQLException {

        return new Classroom(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("capacity"),
                resultSet.getBoolean("disability_accessible"),
                equipment
        );
    }

}
