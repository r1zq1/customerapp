package id.co.bankmandiri.customerapp.dao;

import id.co.bankmandiri.customerapp.domain.Customer;
import id.co.bankmandiri.customerapp.domain.CustomerException;
import id.co.bankmandiri.customerapp.util.DbUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoTest {
    private static CustomerDao dao;
    @BeforeEach
    void setUp() {
        Connection connection = DbUtil.getConnection();
        dao = new CustomerDao(connection);
    }

    @Test
    void displayAllCustomers() {
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer("John", "Doe",
                LocalDate.of(1990,9,9));
        try {
            dao.addCustomer(customer);
            Customer result = dao.findCustomerById(1);
            Assertions.assertEquals("John", result.getFirstName());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Test
    void editCustomer() {
        try {
            Customer customer = dao.findCustomerById(3);
            customer.setFirstName("Tom");
            customer.setLastName("Hanks");
            customer.setDateOfBirth(LocalDate.now());
            dao.editCustomer(customer);
            Customer result = dao.findCustomerById(3);
            Assertions.assertEquals("Tom", result.getFirstName());
            Assertions.assertEquals("Hanks", result.getLastName());
            Assertions.assertEquals(LocalDate.now(), result.getDateOfBirth());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findCustomerById() {
        Exception e = Assertions.assertThrows(
            CustomerException.class, () -> dao.findCustomerById(100)
        );
        Assertions.assertEquals("Customer tidak ditemukan",
                e.getMessage());
    }

    @Test
    void deleteCustomer() {
        try {
            dao.deleteCustomer(2);
            Exception e = Assertions.assertThrows(
                    CustomerException.class,
                    () -> dao.findCustomerById(2)
            );
            Assertions.assertEquals(
                    "Customer tidak ditemukan",
                    e.getMessage());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addCustomerWithID() {
        Customer customer = new Customer(
                1,"John", "Doe",
                LocalDate.of(1990,9,9));
        try {
            dao.addCustomerWithID(customer);
            Customer result = dao.findCustomerById(1);
            Assertions.assertEquals("John", result.getFirstName());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }
}