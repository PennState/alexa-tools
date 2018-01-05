package edu.psu.swe.ssml;

public enum SSMLTimeFormat {
  TWELVE_HOUR_CLOCK("hms12"),
  TWENTY_FOUR_HOUR_CLOCK("hms24");
  
  private String ssmlFormat;
  
  SSMLTimeFormat(String ssmlFormat) {
    this.ssmlFormat = ssmlFormat;
  }
  
  public String asSsml( ) {
    return ssmlFormat;
  }
}
