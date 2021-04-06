package com.epam.esm.persistence;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface TagDAO extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query(value = "SELECT t.name, t.id, count(*) as count FROM mjs_school.orders AS o " +
            "JOIN order_items ot ON ot.id_order=o.id " +
            "JOIN certificates_tags ct ON ct.id_certificate=ot.id_certificate " +
            "JOIN tags t ON t.id=ct.id_tag " +
            "WHERE o.total_sum=(SELECT MAX(total_sum) FROM mjs_school.orders) GROUP BY t.name ORDER BY count(*) DESC ", nativeQuery = true)
    Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders();
}
