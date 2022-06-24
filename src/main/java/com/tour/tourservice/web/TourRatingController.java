package com.tour.tourservice.web;

import com.tour.tourservice.Domain.Tour;
import com.tour.tourservice.Domain.TourRating;
import com.tour.tourservice.Domain.TourRatingPk;
import com.tour.tourservice.repo.TourRatingRepository;
import com.tour.tourservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path="/tours/{tourId}/ratings")
public class TourRatingController {

   private TourRatingRepository tourRatingRepository;
   private TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController(){

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId" ) int tourId,@RequestBody @Validated RatingDto ratingDto){
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour,ratingDto.getCustomerId()),
                ratingDto.getScore(),ratingDto.getComment()));

    }



    private Tour verifyTour(int tourId) throws NoSuchElementException{

        return tourRepository.findById(tourId).orElseThrow(
                ()-> new NoSuchElementException("Tour does not exist "+tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }


}
