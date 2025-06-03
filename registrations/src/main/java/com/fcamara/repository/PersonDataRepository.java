package com.fcamara.repository;

import com.fcamara.model.PersonData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonDataRepository extends JpaRepository<PersonData, Long> {
    Optional<PersonData> findByCpf(String cpf);
    Optional<PersonData> findByCnpj(String cnpj);
}
