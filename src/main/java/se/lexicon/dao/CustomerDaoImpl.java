package se.lexicon.dao;

import se.lexicon.database.DatabaseConnection;
import se.lexicon.model.Customer;
import se.lexicon.model.CustomerType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CustomerDaoImpl implements CustomerDao {

    @Override
    public Customer save(Customer customer) {

        String sql = """
            
                INSERT INTO customer
            (name, email, phone, customer_type, organization_number)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getType().name());
            statement.setString(5, customer.getOrganizationNumber());

            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);

                    return new Customer(
                            generatedId,
                            customer.getName(),
                            customer.getEmail(),
                            customer.getPhone(),
                            customer.getType(),
                            customer.getOrganizationNumber()
                    );
                }
            }
            throw new RuntimeException("Failed to retrieve generated customer id");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save customer", e);
        }
    }

    @Override
    public Optional<Customer> findById(int id) {

        String sql = """
            SELECT *
            FROM customer
            WHERE id = ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(mapCustomer(resultSet));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve customer", e);
        }
    }

    @Override
    public List<Customer> findAll() {

        String sql = """
            SELECT *
            FROM customer
            """;

        List<Customer> customers = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }

            return customers;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve customers", e);
        }
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {

        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                CustomerType.valueOf(rs.getString("customer_type")),
                rs.getString("organization_number")
        );
    }
}