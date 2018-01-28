package edu.psu.swe.ssml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class SpeechBuilderTests {

  @BeforeEach
  public void init() {

  }

  @Test
  public void testSaySomething() {
    String ssml = SpeechBuilder.basic()
                               .say("something")
                               .ssml();

    assertThat(ssml, equalTo("<speak>something</speak>"));
  }

  @Test
  public void testEscape() {
    String ssml = SpeechBuilder.basic()
                               .say("peanut butter & jelly")
                               .ssml();

    assertThat(ssml, equalTo("<speak>peanut butter and jelly</speak>"));
  }

  @Test
  public void testParagraph() {
    String ssml2 = SpeechBuilder.alexa()
                                .sentence(() -> SpeechBuilder.alexa()
                                                             .whisper("hello")
                                                             .say("world"))
                                .say("Something")
                                .ssml();

    String ssml3 = SpeechBuilder.alexa()
                                .sentence("hello world")
                                .say("Something")
                                .ssml();

  }

  @Test
  public void testPauseStrength() {
    String ssml = SpeechBuilder.basic()
                               .say("peanut butter")
                               .pause(BreakStrength.MEDIUM)
                               .say("& jelly")
                               .ssml();

    assertThat(ssml, equalTo("<speak>peanut butter <break strength=\"medium\"/> and jelly</speak>"));
  }

  @Test
  public void testPauseTime() {
    String ssml = SpeechBuilder.basic()
                               .say("peanut butter")
                               .pause(1, ChronoUnit.SECONDS)
                               .say("& jelly")
                               .ssml();

    assertThat(ssml, equalTo("<speak>peanut butter <break time=\"1000ms\"/> and jelly</speak>"));
  }

  @Test
  public void testWithATime() {
    String ssml = AlexaSpeechBuilder.builder()
                                    .say("Know what time it is?")
                                    .time(LocalTime.now())
                                    .ssml();

    System.out.println(ssml);
  }

  @Test
  public void testWithADate() {
    String ssml = AlexaSpeechBuilder.builder()
                                    .say("Know what the date is?")
                                    .date(LocalDate.now(), SSMLDateFormat.DAY_MONTH_YEAR)
                                    .ssml();

    System.out.println(ssml);
  }

  @Test
  public void testWithSayAs() {
    String ssml = AlexaSpeechBuilder.builder()
                                    .say("Know what you can do?")
                                    .sayAs(SsmlSayAsType.EXPLETIVE, "Smile")
                                    .say("widely")
                                    .ssml();

    System.out.println(ssml);
  }
  
  @Test
  public void testPhoneme() {
    String ssml = SpeechBuilder.basic()
                               .phoneme("Edomae", PhoneticSymbol.ɛ, PhoneticSymbol.d, PhoneticSymbol.oʊ, PhoneticSymbol.m, PhoneticSymbol.eɪ)
                               .ssml();
    System.out.println(ssml);

  }
  
  @Test
  public void testFragment() {
    String fragment = SpeechBuilder.basic()
        .say("The venue ")
        .phoneme("Edomae", "EdoUmaI", PhoneticAlphabet.X_SEMPA)
        .say(" is open")
        .weekday(DayOfWeek.MONDAY)
        .say("through")
        .weekday(DayOfWeek.SATURDAY)
        .say("from")
        .time(LocalTime.of(17, 30))
        .say("to")
        .time(LocalTime.of(23, 23))
        .asFragment();
     
    System.out.println(AlexaSpeechBuilder.builder().say(fragment, false).ssml());
  }
  
  @Test
  public void testWithMultiples() {
    String ssml = AlexaSpeechBuilder.builder()
                               .say("Know what the day and time is?")
                               .weekday(DayOfWeek.THURSDAY)
                               .pause(BreakStrength.MEDIUM)
                               .date(LocalDate.now(), SSMLDateFormat.DAY_MONTH_YEAR)
                               .pause(BreakStrength.MEDIUM)
                               .time(LocalTime.now())
                               .ssml();
    
    System.out.println(ssml);
    //assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }
  
  @Test
  public void testProsody() {
    String ssml = AlexaSpeechBuilder.builder().withRate(Rate.X_FAST).withPitch(Pitch.HIGH).withVolume(Volume.LOUD)
                               .say("Know what the day and time is?")
                               .weekday(DayOfWeek.THURSDAY)
                               .pause(BreakStrength.MEDIUM)
                               .date(LocalDate.now(), SSMLDateFormat.DAY_MONTH_YEAR)
                               .pause(BreakStrength.MEDIUM)
                               .time(LocalTime.now())
                               .ssml();
    
    System.out.println(ssml);
    //assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }
}
