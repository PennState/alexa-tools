package conversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.slu.entityresolution.Resolution;
import com.amazon.speech.slu.entityresolution.Resolutions;
import com.amazon.speech.slu.entityresolution.ValueWrapper;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioItem;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.Stream;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.PlayDirective;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseHelper {
  private ResponseHelper() {};
  
  static public SpeechletResponse createDialogIntent(IntentRequest request) {
    log.debug("Creating a dialog intent");

    DialogIntent updatedIntent = new DialogIntent(request.getIntent());
    DelegateDirective delegateDirective = new DelegateDirective();
    delegateDirective.setUpdatedIntent(updatedIntent);

    List<Directive> directives = new ArrayList<>();
    directives.add(delegateDirective);

    SpeechletResponse response = new SpeechletResponse();
    response.setDirectives(directives);
    response.setNullableShouldEndSession(false);
    return response;
  }
  
  static public SimpleCard getSimpleCard(String title, String content) {
    SimpleCard card = new SimpleCard();
    card.setTitle(title);
    card.setContent(content);

    return card;
  }

  static public PlainTextOutputSpeech getPlainTextOutputSpeech(String speechText) {
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    return speech;
  }

  static public SsmlOutputSpeech getSsmlOutputSpeech(String speechText) {
    SsmlOutputSpeech speech = new SsmlOutputSpeech();
    speech.setSsml(speechText);
    return speech;
  }

  static public Reprompt getReprompt(OutputSpeech outputSpeech) {
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(outputSpeech);

    return reprompt;
  }

  static public SpeechletResponse getAskResponse(String cardTitle, String speechText) {
    SimpleCard card = getSimpleCard(cardTitle, speechText);
    PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
    Reprompt reprompt = getReprompt(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }
  
  static public List<String> calculateSlot(Slot eventSlot) {
    String event = eventSlot.getValue();
    log.info("--> Calculating event for spoken word " + event);
    
    Resolutions resolutions = eventSlot.getResolutions();
    if (resolutions == null) {
      return Arrays.asList(eventSlot.getValue());
    }
    
    List<Resolution> resolutionList = resolutions.getResolutionsPerAuthority();
    
    List<String> potentialEvents = new ArrayList<>();
    
    for (Resolution resolution : resolutionList) {
      for (ValueWrapper vw : resolution.getValueWrappers()) {
        potentialEvents.add(vw.getValue().getName());
      }
    }
    
    return potentialEvents;
  }
  
  static public SpeechletResponse createSlotValueSelectionRequest(List<String> options) {
    StringBuilder sb = new StringBuilder();
    sb.append("Did you mean ");
    sb.append(options.stream().collect(Collectors.joining(", or ")));
    String speech = sb.toString();
    
    return ResponseHelper.getAskResponse("What did you mean?", speech);
  }
  
  static public List<Directive> buildPlayDirective(String token, long offset, Map<String, String> tokenUrlMap) {
    log.debug("--> Building the play directive for " + token + " at " + offset);
    Stream stream = new Stream();
    stream.setOffsetInMilliseconds(offset);
    stream.setToken(token);
    String url = tokenUrlMap.get(token).toString();
    log.debug("--> Using url {} ", url);
    stream.setUrl(url);
    AudioItem audioItem = new AudioItem();
    audioItem.setStream(stream);
    PlayDirective playDirective = new PlayDirective();
    playDirective.setAudioItem(audioItem);
    playDirective.setPlayBehavior(PlayBehavior.REPLACE_ALL);

    List<Directive> directives = new ArrayList<>();
    directives.add(playDirective);
    
    return directives;
  }
}
