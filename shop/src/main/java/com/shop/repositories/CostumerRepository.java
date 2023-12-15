package com.shop.repositories;

import com.shop.models.entities.Costumer;
import com.shop.services.CostumerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    boolean existsByCpf(String cpf);

    Object getReferenceByCpf(String cpf);

}
