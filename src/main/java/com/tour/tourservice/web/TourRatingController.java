package com.tour.tourservice.web;

import com.tour.tourservice.Domain.Tour;
import com.tour.tourservice.Domain.TourRating;
import com.tour.tourservice.Domain.TourRatingPk;
import com.tour.tourservice.repo.TourRatingRepository;
import com.tour.tourservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId" ) int tourId, Pageable pageable){
        verifyTour(tourId);
//        return tourRatingRepository.findByPkTourId(tourId).stream()
//                .map(RatingDto::new).collect(Collectors.toList());

        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId,pageable);
        return new PageImpl<>(
                ratings.get().map(RatingDto::new)
                        .collect(Collectors.toList()),
                pageable,ratings.getTotalElements()
        );
        
    }

    @GetMapping(path="/average")
    public Map<String,Double> getAverage(@PathVariable(value = "tourId" ) int tourId){

        verifyTour(tourId);
        return Map.of("average",tourRatingRepository.findByPkTourId(tourId)
                .stream().mapToInt(TourRating::getScore).average()
                .orElseThrow(()
                ->new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value="tourId") int tourId,@RequestBody @Validated RatingDto ratingDto){
        TourRating rating = verifyTourRating(tourId,ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @PatchMapping
    public RatingDto updatedWithPatch(@PathVariable(value="tourId") int tourId,@RequestBody @Validated RatingDto ratingDto){

        TourRating rating = verifyTourRating(tourId,ratingDto.getCustomerId());
        if(ratingDto.getScore()!=null){
            rating.setScore(ratingDto.getScore());
        }
        if(ratingDto.getComment()!=null)
            rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }


    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value="tourId") int tourId, @PathVariable(value="customerId")int customerId){
        TourRating rating = verifyTourRating(tourId,customerId);
        tourRatingRepository.delete(rating);
    }



    private TourRating verifyTourRating(int tourId, Integer customerId) {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId,customerId).orElseThrow(()->
                new NoSuchElementException("Tour-Rating pair for request ("
                + tourId+" for customer "
                +customerId)
        );
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
