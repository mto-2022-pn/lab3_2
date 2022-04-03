package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class OrderTest {
    static class FakeTimeGetter implements CurrentTimeGetter {
        private LocalDateTime date;

        @Override
        public LocalDateTime now() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
    }

    @BeforeEach
    void setUp() {}

    @Test
    void test() {
        FakeTimeGetter timeGetter = new FakeTimeGetter();
        fail("Not yet implemented");
        Order order = new Order(timeGetter);
    }

}
