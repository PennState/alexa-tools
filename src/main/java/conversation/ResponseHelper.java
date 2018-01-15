package conversation;

import java.util.ArrayList;
import java.util.List;

import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
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
}
