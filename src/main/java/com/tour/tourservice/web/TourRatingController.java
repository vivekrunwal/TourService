package com.tour.tourservice.web;

import com.tour.tourservice.Domain.TourRating;
import com.tour.tourservice.repo.TourRatingRepository;
import com.tour.tourservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public void createTourRating(@PathVariable(value = "tourId" ) String tourId,@RequestBody @Validated TourRating tourRating){
         verifyTour(tourId);
         tourRatingRepository.save(new TourRating(tourId,tourRating.getCustomerId(),
                tourRating.getScore(),tourRating.getComment()));
    }

    @GetMapping
    public Page<TourRating> getRatings(@PathVariable(value = "tourId" ) String tourId, Pageable pageable){
//        verifyTour(tourId);
//        return tourRatingRepository.findByPkTourId(tourId).stream()
//                .map(RatingDto::new).collect(Collectors.toList());

//        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId,pageable);
//        return new PageImpl<>(
//                ratings.get().map(RatingDto::new)
//                        .collect(Collectors.toList()),
//                pageable,ratings.getTotalElements()
//        );
        return tourRatingRepository.findByTourId(tourId,pageable);
    }

    @GetMapping(path="/average")
    public Map<String,Double> getAverage(@PathVariable(value = "tourId" ) String tourId){

        verifyTour(tourId);
        return Map.of("average",tourRatingRepository.findByTourId(tourId)
                .stream().mapToInt(TourRating::getScore).average()
                .orElseThrow(()
                ->new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public TourRating updateWithPut(@PathVariable(value="tourId") String tourId,@RequestBody @Validated TourRating tourRating){
        TourRating rating = verifyTourRating(tourId,tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    @PatchMapping
    public TourRating updatedWithPatch(@PathVariable(value="tourId") String tourId,@RequestBody @Validated TourRating tourRating){

        TourRating rating = verifyTourRating(tourId,tourRating.getCustomerId());
        if(tourRating.getScore()!=null){
            rating.setScore(tourRating.getScore());
        }
        if(tourRating.getComment()!=null)
            rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }


    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value="tourId") String tourId, @PathVariable(value="customerId")int customerId){
        TourRating rating = verifyTourRating(tourId,customerId);
        tourRatingRepository.delete(rating);
    }



    private TourRating verifyTourRating(String tourId, Integer customerId) throws NoSuchElementException{
        return tourRatingRepository.findByTourIdAndCustomerId(tourId,customerId).orElseThrow(()->
                new NoSuchElementException("Tour-Rating pair for request ("
                + tourId+" for customer "
                +customerId)
        );
    }


    private void verifyTour(String tourId) throws NoSuchElementException{

       if(!tourRepository.existsById(tourId)){
           throw new NoSuchElementException("Tour does not exist "+tourId);
       }

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }


}
