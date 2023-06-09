package org.cham.customercore.repository;

import org.cham.customercore.domain.CustomerCore;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerCoreRepository extends CrudRepository<CustomerCore, String> {
    Optional<CustomerCore> findById();
    Optional<CustomerCore> findByFirstName(String firstName);
    Optional<CustomerCore> findByUserName(String userName);
    Optional<CustomerCore> findBySsn(String ssn);

    List<CustomerCore> findAll();
    @Override
    <S extends CustomerCore> S save(S entity);
}
