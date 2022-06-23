package com.tour.tourservice.repo;

import com.tour.tourservice.Domain.Tour;
import com.tour.tourservice.Domain.TourPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends CrudRepository<Tour,Integer> {


//    Optional<Tour> findByName(Integer id);

}
