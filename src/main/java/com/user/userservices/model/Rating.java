package com.user.userservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private long ratingID;
    private String userID;
    private String hotelID;
    private int rating;
    private String feedback;
}
