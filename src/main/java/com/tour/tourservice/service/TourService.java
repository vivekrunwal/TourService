package com.tour.tourservice.service;

import com.tour.tourservice.Domain.Tour;
import com.tour.tourservice.Domain.TourPackage;
import com.tour.tourservice.repo.TourPackageRepository;
import com.tour.tourservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Component
public class TourService {


    private TourRepository tourRepository;

    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String tourPackageName, Map<String,String> details){

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(()-> new RuntimeException("Tour package does not exist" + tourPackageName));

        return tourRepository.save(new Tour(title,tourPackage,details));
    }

//    public Tour createTour(String title, String description, String blurb, Integer price,
//                           String duration, String bullets, String keywords, String tourPackageName,
//                           Difficulty difficulty, Region region){
//        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
//                .orElseThrow(()-> new RuntimeException("Tour package does not exist" + tourPackageName));
//        return tourRepository.save(new Tour(title,description,blurb,price,duration,bullets,keywords,tourPackage,
//                difficulty,region));
//    }

    public long total(){
        return tourRepository.count();
    }


}
