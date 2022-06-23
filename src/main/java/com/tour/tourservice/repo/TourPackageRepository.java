package com.tour.tourservice.repo;

import com.tour.tourservice.Domain.TourPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface TourPackageRepository extends CrudRepository<TourPackage,String> {


    Optional<TourPackage> findByName(String name);

}
