//package com.epam.esm.persistence.impl;
//
//import com.epam.esm.criteria_info.OrderCriteriaInfo;
//import com.epam.esm.criteria_info.PageInfo;
//import com.epam.esm.entity.Order;
//import com.epam.esm.persistence.OrderDAO;
//import com.epam.esm.persistence.specification.Specification;
//import com.epam.esm.persistence.specification_builder.impl.OrderSpecificationBuilder;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class OrderDAOImpl extends AbstractCrudDAO<Order, Long, OrderCriteriaInfo> implements OrderDAO {
//
//    private final OrderSpecificationBuilder orderSpecificationBuilder;
//
//    protected OrderDAOImpl(EntityManager entityManager, OrderSpecificationBuilder specificationBuilder) {
//        super(entityManager);
//        this.orderSpecificationBuilder = specificationBuilder;
//    }
//
//    @Override
//    public List<Order> findAll(PageInfo pageInfo, OrderCriteriaInfo criteriaInfo) {
//        List<Specification> specifications = orderSpecificationBuilder.build(criteriaInfo);
//        return entityManager.createQuery(
//                buildCriteriaQuery(specifications, Order.class))
//                .setMaxResults(pageInfo.getLimit())
//                .setFirstResult(pageInfo.getOffset())
//                .getResultList();
//    }
//
//    @Override
//    public Optional<Order> find(Long id) {
//        Order order = entityManager.find(Order.class, id);
//        return Optional.ofNullable(order);
//    }
//
//    @Override
//    public long getCount() {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Order.class)));
//        return entityManager.createQuery(criteriaQuery).getSingleResult();
//    }
//
//    @Override
//    public void update(Order order) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void delete(Order order) {
//        throw new UnsupportedOperationException();
//    }
//}
