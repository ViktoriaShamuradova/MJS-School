package com.epam.esm.persistence.specification;

public final class DefaultSpecification {

    private DefaultSpecification() {
    }

    public static Specification getSpecificationForEqualsByField(String fieldName, Object value) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(root.get(fieldName), value);
    }

    public static Specification getSpecificationForEqualsJoinTableField(String fieldName, String joinFieldName, Object value) {
        return (SearchSpecification) (criteriaBuilder, root) ->
                criteriaBuilder.equal((root.join(fieldName).get(joinFieldName)), value);
    }

    public static Specification getSpecificationForSortAsc(String fieldName) {
        return (SortSpecification) (criteriaBuilder, root) ->
                criteriaBuilder.asc(root.get(fieldName));
    }

    public static Specification getSpecificationForSortDesc(String fieldName) {
        return (SortSpecification) (criteriaBuilder, root) ->
                criteriaBuilder.desc(root.get(fieldName));
    }
}
