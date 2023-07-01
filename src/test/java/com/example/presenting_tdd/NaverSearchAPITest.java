package com.example.presenting_tdd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaverSearchAPITest {


    @Autowired
    private NaverSearchAPI naverSearchAPI;

    @Test
    void testApiCall() {
        NaverSearchAPI.SearchResult result = naverSearchAPI.search("주식", 5, 1, "random");
        assertThat(result.items()).hasSize(5);
    }
}

