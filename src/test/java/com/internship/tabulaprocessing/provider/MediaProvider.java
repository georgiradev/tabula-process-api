package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.TimeOffType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MediaProvider {

  public static Media getMediaInstance() {
    Media media = new Media();

    media.setId(1);
    media.setName("Media");
    media.setPrice(BigDecimal.valueOf(5.99));

    return media;
  }

  public static List<Media> getMediaListInstance() {
    return Collections.singletonList(getMediaInstance());
  }
}
