package edu.psu.swe.ssml;

import lombok.Getter;

public enum BreakStrength {
  NONE("none"),
  X_WEAK("x-weak"),
  WEAK("weak"),
  MEDIUM("medium"),
  STRONG("strong"),
  X_STRONG("x-strong");
  
  @Getter
  private String keyword;

  private BreakStrength(String keyword) {
    this.keyword = keyword;
  }
}
