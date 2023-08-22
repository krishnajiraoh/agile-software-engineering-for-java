package com.sap.cc.ascii;

public class AsciiArtRequest {

    private String toConvert;

    public String getToConvert() {
        return toConvert;
    }

    public String getFontId() {
        return fontId;
    }

    private String fontId;

    public AsciiArtRequest(String toConvert, String fontId){
        this.toConvert = toConvert;
        this.fontId = fontId;
    }
}
