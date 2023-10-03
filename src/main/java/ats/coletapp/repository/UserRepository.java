package ats.coletapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ats.coletapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String email);

    Optional<User> findByLogin(String email);

    Optional<User> findByVerificationCode(String code);

    Optional<User> findByPersonId(Long id);

    void deleteByPersonId(Long id);
    
}
