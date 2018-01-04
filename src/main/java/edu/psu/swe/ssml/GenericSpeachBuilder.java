package edu.psu.swe.ssml;

public class GenericSpeachBuilder extends SpeachBuilder<GenericSpeachBuilder> {

  private GenericSpeachBuilder() {
    
  }
  
  public static GenericSpeachBuilder builder() {
    return new GenericSpeachBuilder();
  }

  @Override
  protected GenericSpeachBuilder getThis() {
    return this;
  }
  
}
