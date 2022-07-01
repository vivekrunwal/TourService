package com.tour.tourservice.repo;


import com.tour.tourservice.Domain.TourRating;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, String> {


    List<TourRating> findByTourId(String tourId);
//    List<TourRating> findByTourId(Integer tourId);
    Page<TourRating> findByTourId(String tourId, Pageable pageable);

    Optional<TourRating> findByTourIdAndCustomerId(String tourId,Integer customerId);

//    Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);



}
