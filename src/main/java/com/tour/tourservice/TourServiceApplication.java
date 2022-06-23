package com.tour.tourservice;

import com.tour.tourservice.Domain.Difficulty;
import com.tour.tourservice.Domain.Region;
import com.tour.tourservice.service.TourPackageService;
import com.tour.tourservice.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class TourServiceApplication implements CommandLineRunner {


    @Autowired
    private TourPackageService tourPackageService;
    @Autowired
    private TourService tourService;
    public static void main(String[] args) {
        SpringApplication.run(TourServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        long numOfPackages = tourPackageService.total();
        createTours("ExploreCalifornia.json");
        long numOfTours = tourService.total();
    }

    private void createTourPackages(){
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");

    }
//    private void loadToursAtStartup() throws IOException{
//
//
//    }
    private void createTours(String fileToImport) throws IOException{

        TourFromFile.read(fileToImport).forEach(t -> tourService.createTour(
                t.title, t.description, t.blurb,
                Integer.parseInt(t.price), t.length, t.bullets, t.keywords,
                t.packageType,
                Difficulty.valueOf(t.difficulty), Region.findByLabel(t.region)
        ));    }

    private static class TourFromFile {
        //attributes as listed in the .json file
        private String packageType, title, description, blurb, price, length, bullets, keywords,  difficulty, region;

        /**
         * Open the ExploreCalifornia.json, unmarshal every entry into a TourFromFile Object.
         *
         * @return a List of TourFromFile objects.
         * @throws IOException if ObjectMapper unable to open file.
         */
        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport),
                            new TypeReference<List<TourFromFile>>(){});
        }


    }


}
