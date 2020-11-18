package com.seasky.sytest.bll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/17
 */
@SpringBootTest
@Transactional
@Rollback
class UserBLLTest {
    @Autowired
    UserBLL userBll;

    @ParameterizedTest
    @MethodSource("getLoginParams")
    void login(String userName, String passWord, Boolean expectedRst) {
        assertEquals(expectedRst, userBll.login(userName, passWord));
    }

    static Stream<Arguments> getLoginParams() {
        return Stream.of(Arguments.of("swg", "123", true), Arguments.of("swg", "1234", false));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void queryUser() {
    }
}