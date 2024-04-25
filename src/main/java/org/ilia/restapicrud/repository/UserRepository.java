package org.ilia.restapicrud.repository;

import org.ilia.restapicrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    List<User> findByBirthDateAfterAndBirthDateBefore(LocalDate from, LocalDate to);
}
