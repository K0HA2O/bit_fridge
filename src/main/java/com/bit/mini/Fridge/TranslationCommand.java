package com.bit.mini.Fridge;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranslationCommand {

    // 영어에서 한글로 번역
    public List<String> translateToKorean(List<String> texts) {
        List<String> translatedTexts = new ArrayList<>();

        // Google Translate API 클라이언트 생성
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        for (String text : texts) {
            Translation translation = translate.translate(
                text,
                Translate.TranslateOption.sourceLanguage("en"), // 원본 언어: 영어
                Translate.TranslateOption.targetLanguage("ko")  // 번역 언어: 한국어
            );
            translatedTexts.add(translation.getTranslatedText());
        }
        return translatedTexts;
    }
}
