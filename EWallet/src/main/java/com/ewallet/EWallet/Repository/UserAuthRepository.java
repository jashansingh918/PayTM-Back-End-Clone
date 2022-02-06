package com.ewallet.EWallet.Repository;

import com.ewallet.EWallet.Model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    @Query("SELECT ua FROM UserAuth where ua.username = :username")
    public UserAuth getUserAuthByUsername(@Param("username") String username);
}
