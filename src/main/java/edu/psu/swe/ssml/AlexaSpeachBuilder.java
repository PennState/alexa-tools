package edu.psu.swe.ssml;

import java.text.MessageFormat;

public class AlexaSpeachBuilder extends SpeachBuilder<AlexaSpeachBuilder> {

  private static final String WHISPER = "<amazon:effect name=\"whispered\">{0}</amazon:effect>";
  
  private AlexaSpeachBuilder() {
    
  }
  
  public SpeachBuilder<AlexaSpeachBuilder> whisper(String words) {
    if (words != null) {
      elements.add(MessageFormat.format(WHISPER, escape(words)));
    }
    
    return this;
  }
  
  public static AlexaSpeachBuilder builder() {
    return new AlexaSpeachBuilder();
  }

  @Override
  protected AlexaSpeachBuilder getThis() {
    return this;
  }
  
}
