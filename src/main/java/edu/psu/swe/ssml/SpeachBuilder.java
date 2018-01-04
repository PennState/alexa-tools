package edu.psu.swe.ssml;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SpeachBuilder<T extends SpeachBuilder<T>> {

  private static final String BREAK_STRENGTH = "<break strength=\"{0}\"/>";
  private static final String BREAK_TIME = "<break time=\"{0,number,#}ms\"/>";

  protected List<String> elements;

  protected SpeachBuilder() {
    this.elements = new ArrayList<>();
  }
  
  public static AlexaSpeachBuilder alexa() {
    return AlexaSpeachBuilder.builder();
  }
  
  public static GenericSpeachBuilder basic() {
    return GenericSpeachBuilder.builder();
  }

  public T say(String words) {
    if (words != null) {
      elements.add(escape(words));
    }
    
    return getThis();
  }
  
  public T pause(int amount, TemporalUnit unit) {
    Duration duration = Duration.of(amount, unit);
    elements.add(MessageFormat.format(BREAK_TIME, duration.toMillis()));

    return getThis();
  }
  
  public T pause(BreakStrength strength) {
    elements.add(MessageFormat.format(BREAK_STRENGTH, strength.getKeyword()));
    
    return getThis();
  }
  
  protected abstract T getThis();

  protected String escape(String word) {
    word = word.replace("&", "and");
    word = word.replace("<", "");
    word = word.replace(">", "");
    word = word.replace("'", "");
    word = word.replace("\"", "");
    return word;
  }

  public String ssml() {
    return "<speak>" + elements.stream()
                               .collect(Collectors.joining(" "))
        + "</speak>";
  }

}
