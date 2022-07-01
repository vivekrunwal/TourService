package com.tour.tourservice.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;


@Document
public class Tour {

    @Id
    private String id;

    @Indexed
    private String title;


    @Indexed
    private String tourPackageCode;

    private Map<String,String> details;

    private String tourPackageName;

    public Tour(String title,TourPackage tourPackage,Map<String, String> details) {
        this.title = title;
        this.details = details;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
    }

    protected Tour(){

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public String getTourPackageName() {
        return tourPackageName;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id='" + id + '\'' +
                ", details=" + details +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id) && Objects.equals(title, tour.title) && Objects.equals(tourPackageCode, tour.tourPackageCode) && Objects.equals(details, tour.details) && Objects.equals(tourPackageName, tour.tourPackageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tourPackageCode, details, tourPackageName);
    }
}
