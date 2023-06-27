package com.example.presenting_tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverSearchAPITest {

  @Autowired
  private NaverSearchAPI naverSearchAPI;

  @Test
  void testGet() {
    SearchResult responseBody = naverSearchAPI.search();
    Assertions.assertThat(responseBody.items()).hasSize(5);
  }
}
