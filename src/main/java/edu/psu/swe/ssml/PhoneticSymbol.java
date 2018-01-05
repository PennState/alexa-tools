package edu.psu.swe.ssml;

import lombok.Getter;

public enum PhoneticSymbol {
  
  //Consonants
  b("b", "b", "voiced bilabial plosive", "bed"),
  d("d", "d", "voiced alveolar plosive", "dig"),
  d͡ʒ("d͡ʒ", "dZ", "voiced postalveolar affricate", "jump"),
  ð("ð", "D", "voiced dental fricative", "then"),
  f("f", "f", "voiceless labiodental fricative", "five"),
  g("g", "g", "voiced velar plosive", "game"),
  h("h", "h", "voiceless glottal fricative", "house"),
  j("j", "j", "palatal approximant", "yes"),
  k("k", "k", "voiceless velar plosive", "cat"),
  l("l", "l", "alveolar lateral approximant", "lay"),
  m("m", "m", "bilabial nasal", "mouse"),
  n("n", "n", "alveolar nasal", "nap"),
  ŋ("ŋ", "N", "velar nasal", "thing"),
  p("p", "p", "voiceless bilabial plosive", "speak"),
  ɹ("ɹ", "r\\", "alveolar approximant", "red"),
  s("s", "s", "voiceless alveolar fricative", "seem"),
  ʃ("ʃ", "S", "voiceless postalveolar fricative", "ship"),
  t("t", "t", "voiceless alveolar plosive", "trap"),
  t͡ʃ("t͡ʃ", "tS", "voiceless postalveolar affricate", "chart"),
  θ("θ", "T", "voiceless dental fricative", "thin"),
  v("v", "v", "voiced labiodental fricative", "vest"),
  w("w", "w", "labial-velar approximant", "west"),
  z("z", "z", "voiced alveolar fricative", "zero"),
  ʒ("ʒ", "Z", "voiced postalveolar fricative", "vision"),

  //Vowels
  ə("ə", "@", "mid central vowel", "arena"),
  ɚ("ɚ", "@`", "mid central r-colored vowel", "reader"),
  æ("æ", "{", "near-open front unrounded vowel", "trap"),
  aɪ("aɪ", "aI", "diphthong", "price"),
  aʊ("aʊ", "aU", "diphthong", "mouth"),
  ɑ("ɑ", "A", "long open back unrounded vowel", "father"),
  eɪ("eɪ", "eI", "diphthong", "face"),
  ɝ("ɝ", "3`", "open-mid central unrounded r-colored vowel", "nurse"),
  ɛ("ɛ", "E", "open-mid front unrounded vowel", "dress"),
  i("i", "i", "long close front unrounded vowel", "fleece"),
  ɪ("ɪ", "I", "near-close near-front unrounded vowel", "kit"),
  oʊ("oʊ", "oU", "diphthong", "goat"),
  ɔ("ɔ", "O", "long open-mid back rounded vowel", "thought"),
  ɔɪ("ɔɪ", "OI", "diphthong", "choice"),
  u("u", "u", "long close back rounded vowel", "goose"),
  ʊ("ʊ", "U", "near-close near-back rounded vowel", "foot"),
  ʌ("ʌ", "V", "open-mid back unrounded vowel", "strut"),
  
  PRIMARY("ˈ", "\"", "primary stress", "Alabama"),
  SECONDARY("ˌ", "%", "secondary stress", "Alabama"),
  SYLLABLE(".", ".", "syllable boundary", "A.la.ba.ma");
  
  @Getter
  private String ipaSymbol;
  
  @Getter
  private String xSempaSymbol;
  
  @Getter
  private String description;
  
  @Getter
  private String example;

  private PhoneticSymbol(String ipaSymbol, String xSempaSymbol, String description, String example) {
    this.ipaSymbol = ipaSymbol;
    this.xSempaSymbol = xSempaSymbol;
    this.description = description;
    this.example = example;
  }
}
