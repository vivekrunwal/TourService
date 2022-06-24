package com.tour.tourservice.repo;


import com.tour.tourservice.Domain.TourRating;
import com.tour.tourservice.Domain.TourRatingPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {


    List<TourRating> findByPkTourId(Integer tourId);
//    List<TourRating> findByTourId(Integer tourId);

    Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId,Integer customerId);

//    Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);



}
