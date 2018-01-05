package edu.psu.swe.ssml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
    String ssml = AlexaSpeechBuilder.builder()
                               .say("Can I tell you a secret?")
                               .whisper("secret")
                               .ssml();
    
    assertThat(ssml, equalTo("<speak>Can I tell you a secret? <amazon:effect name=\"whispered\">secret</amazon:effect></speak>"));
  }
}
