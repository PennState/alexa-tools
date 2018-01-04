package edu.psu.swe.ssml;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SpeechBuilder<T extends SpeechBuilder<T>> {

  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
  DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH mm a");

  
  private static final String BREAK_STRENGTH_TAG = "<break strength=\"{0}\"/>";
  private static final String BREAK_TIME_TAG = "<break time=\"{0,number,#}ms\"/>";
  private static final String EMPHASIS_TAG = "<emphasis level=\"{1}\">{0}</emphasis>";
  private static final String SAY_AS_TAG = "<say-as interpret-as=\"{0}\">{1}</say-as>";
  private static final String SAY_AS_DATE_TAG = "<say-as interpret-as=\"date\" format=\"{0}\">{1}</say-as>";

  protected List<String> elements;

  protected SpeechBuilder() {
    this.elements = new ArrayList<>();
  }
  
  public static AlexaSpeechBuilder alexa() {
    return AlexaSpeechBuilder.builder();
  }
  
  public static GenericSpeechBuilder basic() {
    return GenericSpeechBuilder.builder();
  }

  public T say(String words) {
    if (words != null) {
      elements.add(escape(words));
    }
    
    return getThis();
  }
  
  public T pause(int amount, TemporalUnit unit) {
    Duration duration = Duration.of(amount, unit);
    elements.add(MessageFormat.format(BREAK_TIME_TAG, duration.toMillis()));

    return getThis();
  }
  
  public T pause(BreakStrength strength) {
    elements.add(MessageFormat.format(BREAK_STRENGTH_TAG, strength.getValue()));
    
    return getThis();
  }
  
  public T emphasize(String words, EmphasisType type) {
    elements.add(MessageFormat.format(EMPHASIS_TAG, escape(words), type.getValue()));
    
    return getThis();
  }
  
  public T date(LocalDate localDate, SSMLDateFormat format) {
    elements.add(MessageFormat.format(SAY_AS_DATE_TAG, format.asSsml(), localDate.format(dateTimeFormatter)));
    
    return getThis();
  }
  
  public T time(LocalTime localTime) {
    elements.add(localTime.format(timeFormatter));
    
    return getThis();  
  }
  
  public T sayAs(SsmlSayAsType type, String value) {
    elements.add(MessageFormat.format(SAY_AS_TAG, type.asSsml(), value));
    return getThis();
  }
  
  public T weekday(DayOfWeek dayOfWeek) {
    elements.add(dayOfWeek.name());
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
