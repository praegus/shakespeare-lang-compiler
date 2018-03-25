package nl.java.shakespearelang.parser;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RomanToArabicConverterTest {

    @Test
    public void valid_numbers() {
        assertThat(RomanToArabicConverter.romanToArabic("XXXIII")).isEqualTo(33);
        assertThat(RomanToArabicConverter.romanToArabic("XCIV")).isEqualTo(94);
        assertThat(RomanToArabicConverter.romanToArabic("CXLVII")).isEqualTo(147);
        assertThat(RomanToArabicConverter.romanToArabic("CCCLXV")).isEqualTo(365);
        assertThat(RomanToArabicConverter.romanToArabic("CD")).isEqualTo(400);
        assertThat(RomanToArabicConverter.romanToArabic("CCLXXXIX")).isEqualTo(289);
        assertThat(RomanToArabicConverter.romanToArabic("MMMDXLVIII")).isEqualTo(3548);
        assertThat(RomanToArabicConverter.romanToArabic("MMCMLXXI")).isEqualTo(2971);
    }

    @Test
    public void invalid_number() {
        assertThatThrownBy(() -> RomanToArabicConverter.romanToArabic("woief")).isInstanceOf(RuntimeException.class).hasMessage("Not a valid Roman numeral!");
    }

}