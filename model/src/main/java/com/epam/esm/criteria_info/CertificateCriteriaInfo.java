package com.epam.esm.criteria_info;

import java.util.List;
//здесь же и провалидировать

/**
 * Класс, содержащий информацию из url для построения запросов
 */
public class CertificateCriteriaInfo extends CriteriaInfo {
    private String byDateSortingWay;
    private String byNameSortingWay;
    private List<String> tagNames;
    private String partOfNameOrDescription;

    public CertificateCriteriaInfo() {
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getPartOfNameOrDescription() {
        return partOfNameOrDescription;
    }

    public void setPartOfNameOrDescription(String partOfNameOrDescription) {
        this.partOfNameOrDescription = partOfNameOrDescription;
    }

    public String getByDateSortingWay() {
        return byDateSortingWay;
    }

    public void setByDateSortingWay(String byDateSortingWay) {
        this.byDateSortingWay = byDateSortingWay;
    }

    public String getByNameSortingWay() {
        return byNameSortingWay;
    }

    public void setByNameSortingWay(String byNameSortingWay) {
        this.byNameSortingWay = byNameSortingWay;
    }

    @Override
    public String toString() {
        return "CertificateCriteriaInfo{" +
                "byDateSortingWay='" + byDateSortingWay + '\'' +
                ", byNameSortingWay='" + byNameSortingWay + '\'' +
                ", tagNames=" + tagNames +
                ", partOfNameOrDescription='" + partOfNameOrDescription + '\'' +
                '}';
    }

//    private boolean isValid(String value) {
//        return value.trim().toLowerCase().equals(Constant.ASC) || value.trim().toLowerCase().equals(Constant.DESC);
//    }



//    private static class Constant {
//        private final static String ASC = "asc";
//        private final static String DESC = "desc";
//    }
}
