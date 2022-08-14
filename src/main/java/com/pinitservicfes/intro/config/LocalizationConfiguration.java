package com.pinitservicfes.intro.config;

import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 *
 * @author Ramdane
 */
@Log
@Configuration
public class LocalizationConfiguration implements WebMvcConfigurer {

    private static final Locale FRENCH = new Locale("fr");
    private static final Locale ENGLISH = Locale.ENGLISH;
    private static final Locale ARABIC = new Locale("ar");

    private static final Locale DEFAULT = FRENCH;

    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                var lang = request.getHeader("lang");
                return fromLangName(lang);
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                log.info("Set locale called " + locale);
            }
        };
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {

        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");

        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public MessageSource messageSource() {
        var bundle = new ResourceBundleMessageSource();
        bundle.setBasename("lang/messages");
        bundle.setDefaultLocale(DEFAULT);
        bundle.setUseCodeAsDefaultMessage(true);
        return bundle;
    }

    public static Locale fromLangName(String lang) {

        if (lang == null) {
            return DEFAULT;
        }
        var _lang = lang.toLowerCase();

        return switch (_lang) {
            case "en" -> ENGLISH;
            case "fr" -> FRENCH;
            case "ar" -> ARABIC;
            default -> DEFAULT;

        };
    }

}
