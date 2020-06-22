package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Media;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class MediaProvider {

  public static Media getMediaInstance() {
    Media media = new Media();

    media.setId(1);
    media.setName("Media");
    media.setPrice(BigDecimal.valueOf(5.99));

    return media;
  }
}
