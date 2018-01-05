package edu.psu.swe.ssml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlexaSpeachBuilderTests {

  @BeforeEach
  public void init() {

  }

  @Test
  public void testWhisperSomething() {
    String ssml = AlexaSpeachBuilder.builder()
                               .say("Can I tell you a secret?")
                               .whisper("secret")
                               .ssml();
    
    assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }
  
  @Test
  public void testWithATime() {
    String ssml = AlexaSpeachBuilder.builder()
                               .say("Know what time it is?")
                               .time(LocalTime.now())
                               .ssml();
    
    System.out.println(ssml);
    //assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }

  @Test
  public void testWithADate() {
    String ssml = AlexaSpeachBuilder.builder()
                               .say("Know what the date is?")
                               .date(LocalDate.now(), SSMLDateFormat.DAY_MONTH_YEAR)
                               .ssml();
    
    System.out.println(ssml);
    //assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }
  
  @Test
  public void testWithSayAs() {
    String ssml = AlexaSpeachBuilder.builder()
                               .say("Know what you can do?")
                               .sayAs(SsmlSayAsType.EXPLETIVE, "Smile")
                               .say("widely")
                               .ssml();
    
    System.out.println(ssml);
    //assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }
  
  @Test
  public void testWithMultiples() {
    String ssml = AlexaSpeachBuilder.builder()
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
