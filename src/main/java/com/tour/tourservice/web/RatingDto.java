package com.tour.tourservice.web;

import com.tour.tourservice.Domain.TourRating;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max=255)
    private String comment;

    @NotNull
    private Integer customerId;

    protected RatingDto() {}

    public RatingDto(Integer score, String comment, Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    public RatingDto(TourRating tourRating) {
            this.score = tourRating.getScore();
            this.comment = tourRating.getComment();
            this.customerId = tourRating.getPk().customerId;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
