package com.sap.cc.unittesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RomanNumeralsTest {

    private RomanNumerals romanNumerals;

    @BeforeEach
    public void setup() {
        romanNumerals = new RomanNumerals();
    }

    @Test
    public void should_returnNum_for_singleRomans(){
        assertThat(romanNumerals.toArabic("I")).isEqualTo(1);
        assertThat(romanNumerals.toArabic("V")).isEqualTo(5);
        assertThat(romanNumerals.toArabic("M")).isEqualTo(1000);
    }

    @Test
    public void should_returnErrorPaths_for_invalidNumerals(){
        assertThat(romanNumerals.toArabic("E")).isEqualTo(-1);
        assertThat(romanNumerals.toArabic("")).isEqualTo(-1);
        assertThat(romanNumerals.toArabic(null)).isEqualTo(-1);
        assertThat(romanNumerals.toArabic("IIX")).isEqualTo(-1);
    }

    @Test
    public void should_returnValid_for_additiveRomans(){
        assertThat(romanNumerals.toArabic("II")).isEqualTo(2);
        assertThat(romanNumerals.toArabic("VI")).isEqualTo(6);
        assertThat(romanNumerals.toArabic("CXI")).isEqualTo(111);
    }

    @Test
    public void should_returnValid_for_subtractiveRomans(){
        assertThat(romanNumerals.toArabic("IV")).isEqualTo(4);
        assertThat(romanNumerals.toArabic("XL")).isEqualTo(40);
        assertThat(romanNumerals.toArabic("XC")).isEqualTo(90);
    }

    @Test
    public void should_returnValid_for_complexNumerals(){
        assertThat(romanNumerals.toArabic("XIV")).isEqualTo(14);
        assertThat(romanNumerals.toArabic("CMXL")).isEqualTo(940);
        assertThat(romanNumerals.toArabic("MMXXI")).isEqualTo(2021);
    }
}
