package edu.psu.swe.ssml;

import lombok.Getter;

public enum PhoneticAlphabet {
  
  IPA("ipa"), X_SEMPA("x-sampa");
  
  @Getter
  private String value;

  private PhoneticAlphabet(String value) {
    this.value = value;
  }
}
