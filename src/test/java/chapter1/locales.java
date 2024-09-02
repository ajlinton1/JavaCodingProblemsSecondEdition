package chapter1;

import org.junit.*;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class locales {

    @Test
    public void problem17_locales() {
        Locale locale = new Locale("en", "US");
        assertNotNull(locale);

        var locale1 = Locale.getDefault();
        assertNotNull(locale1);

        var locales = Locale.getAvailableLocales();
        assertNotNull(locales);

        var locale2 = new Locale.Builder().setLanguage("en").setRegion("US").build();
        assertNotNull(locale2);

        var locale3 = Locale.US;
        assertNotNull(locale3);

        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse("en-US;q=0.8,en;q=0.6");

        // Get the best matching locale
        Locale locale4= Locale.lookup(languageRanges, List.of(Locale.getAvailableLocales()));
        assertNotNull(locale4);

        List<Locale.LanguageRange> languagePriorityList = Locale.LanguageRange.parse("en-GB;q=1.0,fr-FR;q=0.9");

        // Get the best matching locale
        Locale bestMatchingLocale = Locale.lookup(languagePriorityList, List.of(Locale.getAvailableLocales()));
        assertNotNull(bestMatchingLocale);
    }

}
