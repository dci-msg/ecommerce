package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Id(Long userId);

    List<Cart> findAllByUser_IdAndAndIsOpenTrue(Long userId);

}
