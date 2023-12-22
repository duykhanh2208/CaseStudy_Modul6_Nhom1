package com.houserenting.service;

import com.houserenting.model.Review;

import java.util.List;

public interface ReviewService extends GeneralService<Review> {
    List<Review> FindListReviewByHouseID(Long id);
    List<Review> checkReviewRecordInCaseUserCommentTwoTimes(Long id);
}
