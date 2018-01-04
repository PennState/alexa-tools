package edu.psu.swe.ssml;

public enum SSMLDateFormat {
  MONTH_DAY_YEAR("mdy"),
  DAY_MONTH_YEAR("dmy"),
  YEAR_MONTH_DAY("ymd"),
  MONTH_DAY("md"),
  DAY_MONTH("dm"),
  YEAR_MONTH("ym"),
  MONTH_YEAR("my"),
  DAY("d"),
  MONTH("m"),
  YEAR("y");
  
  private String ssmlFormat;
  
  SSMLDateFormat(String ssmlFormat) {
    this.ssmlFormat = ssmlFormat;
  }
  
  public String asSsml( ) {
    return ssmlFormat;
  }
}
