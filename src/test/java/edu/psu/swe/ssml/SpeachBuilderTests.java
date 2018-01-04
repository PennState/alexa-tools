package edu.psu.swe.ssml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.temporal.ChronoUnit;

public class SpeachBuilderTests {

  @BeforeEach
  public void init() {

  }

  @Test
  public void testSaySomething() {
    String ssml = SpeachBuilder.basic()
                               .say("something")
                               .ssml();
    
    assertThat(ssml, equalTo("<speak>something</speak>"));
  }

  @Test
  public void testEscape() {
    String ssml = SpeachBuilder.basic()
                               .say("peanut butter & jelly")
                               .ssml();
    
    assertThat(ssml, equalTo("<speak>peanut butter and jelly</speak>"));
  }
  
  @Test
  public void testPauseStrength() {
    String ssml = SpeachBuilder.basic()
                               .say("peanut butter")
                               .pause(BreakStrength.MEDIUM)
                               .say("& jelly")
                               .ssml();
    
    assertThat(ssml, equalTo("<speak>peanut butter <break strength=\"medium\"/> and jelly</speak>"));
  }
  
  @Test
  public void testPauseTime() {
    String ssml = SpeachBuilder.basic()
                               .say("peanut butter")
                               .pause(1, ChronoUnit.SECONDS)
                               .say("& jelly")
                               .ssml();
    
    assertThat(ssml, equalTo("<speak>peanut butter <break time=\"1000ms\"/> and jelly</speak>"));
  }
}
