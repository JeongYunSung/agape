package com.yunseong.core.payment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select p from Payment p inner join fetch p.order o where o.id  = :id")
    Optional<Payment> findById(long id);

    @Query("select p from Payment p inner join fetch p.order o inner join fetch o.orderer where o.id  = :id")
    Optional<Payment> findMemberById(long id);

    @Query("select p from Payment p inner join fetch p.order o inner join fetch o.orders where o.id = :id")
    Optional<Payment> findFetchById(long id);
}
