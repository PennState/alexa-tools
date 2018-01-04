package edu.psu.swe.ssml;

import lombok.Getter;

public enum BreakStrength {
  NONE("none"), X_WEAK("x-weak"), WEAK("weak"), MEDIUM("medium"), STRONG("strong"), X_STRONG("x-strong");

  @Getter
  private String value;

  private BreakStrength(String value) {
    this.value = value;
  }
}
