package com.epam.esm.criteria_info;

import java.util.List;
import java.util.Objects;

/**
 * Class containing information about certificate from url to build desired query
 */
public class CertificateCriteriaInfo extends CriteriaInfo {
    private String byDateSortingWay;
    private String byNameSortingWay;
    private List<String> tagNames;
    private String partOfNameOrDescription;
    private String name;

    public CertificateCriteriaInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CertificateCriteriaInfo that = (CertificateCriteriaInfo) o;
        return Objects.equals(byDateSortingWay, that.byDateSortingWay) && Objects.equals(byNameSortingWay, that.byNameSortingWay) && Objects.equals(tagNames, that.tagNames) && Objects.equals(partOfNameOrDescription, that.partOfNameOrDescription) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), byDateSortingWay, byNameSortingWay, tagNames, partOfNameOrDescription, name);
    }

    @Override
    public String toString() {
        return "CertificateCriteriaInfo{" +
                "byDateSortingWay='" + byDateSortingWay + '\'' +
                ", byNameSortingWay='" + byNameSortingWay + '\'' +
                ", tagNames=" + tagNames +
                ", partOfNameOrDescription='" + partOfNameOrDescription + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
