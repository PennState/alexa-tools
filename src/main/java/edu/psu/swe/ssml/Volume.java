package edu.psu.swe.ssml;

import lombok.Getter;

public enum Volume {
  SILENT("silent"),
  X_SOFT("x-soft"),
  SOFT("soft"),
  MEDIUM("medium"),
  LOUD("loud"),
  X_LOUD("x-loud");
  
  @Getter
  private String value;

  private Volume(String value) {
    this.value = value;
  }
}
