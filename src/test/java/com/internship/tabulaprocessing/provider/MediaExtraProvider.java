package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.MediaExtra;

import java.math.BigDecimal;
import java.util.Set;

public class MediaExtraProvider {

  public static MediaExtra getMediaExtraInstance() {
    MediaExtra mediaExtra = new MediaExtra();

    mediaExtra.setId(1);
    mediaExtra.setName("Media Extra");
    mediaExtra.setPrice(BigDecimal.valueOf(4.99));

    return mediaExtra;
  }
}
