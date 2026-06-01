package com.example.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        // Arrange chung cho mỗi test
        service = new AccountService();
    }

    @ParameterizedTest(name = "Email hợp lệ: {0}")
    @ValueSource(strings = {
            "john@example.com",
            "alice.b@mail.co.uk",
            "carol_99@domain.io"
    })
    @DisplayName("isValidEmail trả về true với email đúng định dạng")
    void isValidEmail_ValidEmails_ReturnsTrue(String email) {
        // Act
        boolean actual = service.isValidEmail(email);

        // Assert
        assertTrue(actual);
    }

    @ParameterizedTest(name = "Email không hợp lệ: {0}")
    @CsvSource(value = {
            "bobmail.com",
            "missing@dot",
            "'@nodomain.com'",
            "' '",
            "NULL"
    }, nullValues = "NULL")
    @DisplayName("isValidEmail trả về false với email sai hoặc null")
    void isValidEmail_InvalidEmails_ReturnsFalse(String email) {
        // Act
        boolean actual = service.isValidEmail(email);

        // Assert
        assertFalse(actual);
    }

    @ParameterizedTest(name = "Row {index}: username={0}, password={1}, email={2}, expected={3}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("registerAccount kiểm thử bằng dữ liệu từ CSV")
    void registerAccount_FromCsv_ReturnsExpectedResult(String username,
                                                       String password,
                                                       String email,
                                                       boolean expected) {
        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("registerAccount: password đúng 6 ký tự trả về false")
    void registerAccount_PasswordExactly6_ReturnsFalse() {
        // Arrange
        String username = "bob";
        String password = "abcdef";
        String email = "bob@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertFalse(actual);
    }

    @Test
    @DisplayName("registerAccount: password đúng 7 ký tự trả về true")
    void registerAccount_PasswordExactly7_ReturnsTrue() {
        // Arrange
        String username = "bob";
        String password = "abcdefg";
        String email = "bob@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertTrue(actual);
    }

    @Test
    @DisplayName("registerAccount: tất cả tham số null trả về false")
    void registerAccount_AllNull_ReturnsFalse() {
        // Act
        boolean actual = service.registerAccount(null, null, null);

        // Assert
        assertFalse(actual);
    }

    @Test
    @DisplayName("registerAccount: username chỉ có khoảng trắng trả về false")
    void registerAccount_BlankUsername_ReturnsFalse() {
        // Arrange
        String username = "   ";
        String password = "password123";
        String email = "test@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertFalse(actual);
    }
}
