package edu.psu.swe.ssml;

import lombok.Getter;

public enum Pitch {
  X_LOW("x-low"),
  LOW("low"),
  MEDIUM("medium"),
  HIGH("high"),
  X_HIGH("x-high");
  
  @Getter
  private String value;

  private Pitch(String value) {
    this.value = value;
  }
}
