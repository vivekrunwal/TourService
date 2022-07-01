package com.tour.tourservice;

import com.tour.tourservice.service.TourPackageService;
import com.tour.tourservice.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
               t.getTitle(),t.getPackageName(),t.getDetails()
        ));
    }

    private static class TourFromFile {
        //attributes as listed in the .json file
//        private String packageType, title, description, blurb, price, length, bullets, keywords,  difficulty, region;
        String title;
        String packageName;
        Map<String,String> details;

        TourFromFile(Map<String,String> record){
            this.title = record.get("title");
            this.packageName = record.get("packageType");
            this.details = record;
            this.details.remove("packageType");
            this.details.remove("title");
        }
        /**
         * Open the ExploreCalifornia.json, unmarshal every entry into a TourFromFile Object.
         *
         * @return a List of TourFromFile objects.
         * @throws IOException if ObjectMapper unable to open file.
         */
        static List<TourFromFile> read(String fileToImport) throws IOException {
//            return new ObjectMapper().setVisibility(FIELD, ANY).
//                    readValue(new FileInputStream(fileToImport),
//                            new TypeReference<List<TourFromFile>>(){});
            List<Map<String,String>> records = new ObjectMapper().setVisibility(FIELD,ANY)
                    .readValue(new FileInputStream(fileToImport), new TypeReference<List<Map<String, String>>>() {
                    });
            return records.stream().map(TourFromFile::new).collect(Collectors.toList());
        }

        public String getTitle() {
            return title;
        }

        public Map<String, String> getDetails() {
            return details;
        }
        public String getPackageName() {
            return packageName;
        }


    }


}
