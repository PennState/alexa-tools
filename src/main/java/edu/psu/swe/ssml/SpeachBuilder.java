package edu.psu.swe.ssml;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpeachBuilder {

  private static final String BREAK_STRENGTH = "<break strength=\"{0}\"/>";
  private static final String BREAK_TIME = "<break time=\"{0,number,#}ms\"/>";

  private List<String> elements;

  public SpeachBuilder() {
    this.elements = new ArrayList<>();
  }

  public SpeachBuilder say(String words) {
    if (words != null) {
      elements.add(escape(words));
    }
    
    return this;
  }
  
  public SpeachBuilder pause(int amount, TemporalUnit unit) {
    Duration duration = Duration.of(amount, unit);
    elements.add(MessageFormat.format(BREAK_TIME, duration.toMillis()));

    return this;
  }
  
  public SpeachBuilder pause(BreakStrength strength) {
    elements.add(MessageFormat.format(BREAK_STRENGTH, strength.getKeyword()));
    
    return this;
  }

  public static SpeachBuilder builder() {
    return new SpeachBuilder();
  }

  private String escape(String word) {
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
