package edu.psu.swe.ssml;

import lombok.Getter;

public enum EmphasisType {
  STRONG("strong"), MODERATE("moderate"), REDUCED("reduced");

  @Getter
  private String value;

  private EmphasisType(String value) {
    this.value = value;
  }

}
