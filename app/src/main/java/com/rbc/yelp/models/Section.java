package com.rbc.yelp.models;

import java.util.List;

public class Section {

    String sectionName;
    List<Business> businessList;

    public Section(String sectionName, List<Business> businessList) {
        this.sectionName = sectionName;
        this.businessList = businessList;
    }

    public String getSectionName() {
        return sectionName;
    }

    public List<Business> getBusinesses() {
        return businessList;
    }

}
