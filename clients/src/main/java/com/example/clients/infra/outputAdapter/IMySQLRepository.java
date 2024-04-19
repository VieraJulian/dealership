package com.example.clients.infra.outputAdapter;

import com.example.clients.domain.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMySQLRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c FROM Client c")
    public List<Client> getClientList(Pageable pageable);
}
