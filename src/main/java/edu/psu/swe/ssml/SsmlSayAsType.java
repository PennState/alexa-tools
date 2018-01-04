package edu.psu.swe.ssml;

public enum SsmlSayAsType {
  CHARACTERS("characters"),
  SPELL_OUT("spell-out"),
  CARDINAL("cardinal"),
  NUMBER("number"),
  ORDINAL("ordinal"),
  DIGITS("digits"),
  FRACTION("fraction"),
  UNIT("unit"),
  TIME("time"),
  TELEPHONE("telephone"),
  ADDRESS("address"),
  INTERJECTION("interjection"),
  EXPLETIVE("expletive");
  
  private String ssmlFormat;
  
  SsmlSayAsType(String ssmlFormat) {
    this.ssmlFormat = ssmlFormat;  
  }
  
  public String asSsml() {
    return ssmlFormat;
  }
}
