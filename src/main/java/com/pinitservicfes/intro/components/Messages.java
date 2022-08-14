/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pinitservicfes.intro.components;

import static com.pinitservicfes.intro.config.LocalizationConfiguration.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ramdane
 */
@Component
@RequiredArgsConstructor
public class Messages {

    private final MessageSource src;

    public String msg(String code, String lang) {

        return src.getMessage(code, null, fromLangName(lang));
    }

    public String msg(String code) {
        return src.getMessage(code, null, fromLangName(null));
    }

    public String msg(String code, String lang, Object... args) {
        return src.getMessage(code, args, fromLangName(lang));
    }

    public String getUserLang(String userId) {
        return "en";
    }

}
