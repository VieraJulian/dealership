package com.example.clients.infra.outputAdapter;

import com.example.clients.domain.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Sql("/test-mysql.sql")
public class IMySQLRepositoryTest {

    @Autowired
    IMySQLRepository iMySQLRepository;

    @Test
    void testGetClientList(){
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);

        List<Client> clientList = iMySQLRepository.getClientList(pageable);

        assertEquals(3, clientList.size());

    }
}
