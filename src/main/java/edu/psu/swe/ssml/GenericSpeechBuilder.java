package edu.psu.swe.ssml;

public class GenericSpeechBuilder extends SpeechBuilder<GenericSpeechBuilder> {

  private GenericSpeechBuilder() {
    
  }
  
  public static GenericSpeechBuilder builder() {
    return new GenericSpeechBuilder();
  }

  @Override
  protected GenericSpeechBuilder getThis() {
    return this;
  }
  
}
