package com.HiddenGems.backend.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewRepositoryTest {

    @Test
    void simpleAverageRatingTest() {
        double average = (5 + 3) / 2.0;

        assertEquals(4.0, average, 0.001);
    }
}