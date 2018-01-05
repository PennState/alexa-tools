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
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SpeechBuilder<T extends SpeechBuilder<T>> {

  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
  //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH mm a");

  private static final String BREAK_STRENGTH_TAG = "<break strength=\"{0}\"/>";
  private static final String BREAK_TIME_TAG = "<break time=\"{0,number,#}ms\"/>";
  private static final String EMPHASIS_TAG = "<emphasis level=\"{1}\">{0}</emphasis>";
  private static final String SAY_AS_TAG = "<say-as interpret-as=\"{0}\">{1}</say-as>";
  private static final String SAY_AS_DATE_TAG = "<say-as interpret-as=\"date\" format=\"{0}\">{1}</say-as>";
  private static final String PHONEME_TAG = "<phoneme alphabet=\"{0}\" ph=\"{1}\">{2}</phoneme>";

  private static final String SENTENCE_TAG = "<s>{0}</s>";
  private static final String PARAGRAPH_TAG = "<p>{0}</p>";

  protected List<String> elements;
  
  protected Pitch pitch;
  protected Rate rate;
  protected Volume volume;

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
      addElement(escape(words));
    }

    return getThis();
  }
  
  public T say(Supplier<SpeechBuilder<?>> contents) {
    String ssml = contents.get().ssml(true);
    addElement(ssml);
    
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
    addElement(MessageFormat.format(EMPHASIS_TAG, escape(words), type.getValue()));

    return getThis();
  }

  public T date(LocalDate localDate, SSMLDateFormat format) {
    addElement(MessageFormat.format(SAY_AS_DATE_TAG, format.asSsml(), localDate.format(dateTimeFormatter)));

    return getThis();
  }

  public T time(LocalTime localTime) {
    int hours = localTime.getHour();
    int minutes = localTime.getMinute();
    boolean pm = false;
    boolean onTheHour = false;
    
    if (hours > 12) {
      pm = true;
      hours -= 12;
    }
    
    if (minutes == 0) {
      onTheHour = true;
    }
    
    String minutesString = Integer.toString(minutes);
    if (minutes < 10 && !onTheHour) {
      minutesString = "0" + Integer.toString(minutes);  
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append(hours);
    if (onTheHour) {
      sb.append(" oclock");
    } else {
      sb.append(" ");
      sb.append(minutesString);
    }
    
    if (pm) {
      sb.append(" PM");
    } else {
      sb.append(" AM");
    }
    
    addElement(sb.toString());

    return getThis();
  }

  public T sayAs(SsmlSayAsType type, String value) {
    addElement(MessageFormat.format(SAY_AS_TAG, type.asSsml(), value));
    return getThis();
  }

  public T weekday(DayOfWeek dayOfWeek) {
    addElement(dayOfWeek.name());
    return getThis();
  }

  public T phoneme(String word, PhoneticSymbol... phonemeSymbols) {
    String phoneme = Stream.of(phonemeSymbols)
                           .map(PhoneticSymbol::getIpaSymbol)
                           .collect(Collectors.joining());
    return phoneme(word, phoneme, PhoneticAlphabet.IPA);
  }

  public T phoneme(String word, String phoneme, PhoneticAlphabet type) {
    addElement(MessageFormat.format(PHONEME_TAG, type.getValue(), phoneme, escape(word)));
    return getThis();
  }

  public T sentence(String contents) {
    addElement(MessageFormat.format(SENTENCE_TAG, contents));
    return getThis();
  }
  
  public T sentence(Supplier<SpeechBuilder<?>> contents) {
    return sentence(contents.get().ssml(true));
  }

  public T paragraph(String contents) {
    addElement(MessageFormat.format(PARAGRAPH_TAG, contents));
    return getThis();
  }
  
  public T paragraph(Supplier<SpeechBuilder<?>> contents) {
    return paragraph(contents.get().ssml(true));
  }

  public T withPitch(Pitch pitch) {
    this.pitch = pitch;
    return getThis();
  }
  
  public T withRate(Rate rate) {
    this.rate = rate;
    return getThis();
  }
  
  public T withVolume(Volume volume) {
    this.volume = volume;
    return getThis();
  }
  
  private void addElement(String contents) {
    String checkWrapWithProsody = checkWrapWithProsody(contents);
    this.elements.add(checkWrapWithProsody);
    resetProsody();
  }
  
  private String checkWrapWithProsody(String contents) {
    int numParams = 0;
    if (rate != null) {
      numParams++;
    }
    if (pitch != null) {
      numParams++;
    }
    if (volume != null) {
      numParams++;
    }
    if (numParams == 0) {
      return contents;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("<prosody ");
    
    if (pitch != null) {
      sb.append("pitch=\"").append(pitch.getValue()).append("\"");
      sb.append(numParams-- > 1 ? " " : "");
    }
    
    if (rate != null) {
      sb.append("rate=\"").append(rate.getValue()).append("\"");
      sb.append(numParams > 1 ? " " : "");
    }
    
    if (volume != null) {
      sb.append("volume=\"").append(volume.getValue()).append("\"");
    }
    
    sb.append(">");
    
    sb.append(contents);
    
    sb.append("</prosody>");
    
    return sb.toString();
  }
  
  private void resetProsody() {
    pitch = null;
    rate = null;
    volume = null;
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
    return ssml(false);
  }

  String ssml(boolean embed) {
    StringBuilder sb = new StringBuilder();

    if (!embed) {
      sb.append("<speak>");
    }
    sb.append(elements.stream()
                      .collect(Collectors.joining(" ")));

    if (!embed) {
      sb.append("</speak>");
    }

    return sb.toString();
  }

}
