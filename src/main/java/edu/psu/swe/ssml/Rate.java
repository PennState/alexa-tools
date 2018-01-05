package edu.psu.swe.ssml;

import lombok.Getter;

public enum Rate {
  X_SLOW("x-slow"),
  SLOW("slow"),
  MEDIUM("medium"),
  FAST("fast"),
  X_FAST("x-fast");
  
  @Getter
  private String value;

  private Rate(String value) {
    this.value = value;
  }
}
