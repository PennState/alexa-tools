package edu.psu.swe.ssml;

import java.text.MessageFormat;

public class AlexaSpeechBuilder extends SpeechBuilder<AlexaSpeechBuilder> {

  private static final String WHISPER = "<amazon:effect name=\"whispered\">{0}</amazon:effect>";
  
  private AlexaSpeechBuilder() {
    
  }
  
  public SpeechBuilder<AlexaSpeechBuilder> whisper(String words) {
    if (words != null) {
      elements.add(MessageFormat.format(WHISPER, escape(words)));
    }
    
    return this;
  }
  
  public static AlexaSpeechBuilder builder() {
    return new AlexaSpeechBuilder();
  }

  @Override
  protected AlexaSpeechBuilder getThis() {
    return this;
  }
  
}
