package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
}
