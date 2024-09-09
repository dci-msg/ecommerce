package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
}
