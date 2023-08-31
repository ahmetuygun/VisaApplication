package com.krakozhia.visa.visaApplication.domain.info;

public enum FictionalCountry {
    NOVARVIA("NVR"),
    ZEPHYRIA("ZPH"),
    ELDORIA("ELD"),
    LUMINEA("LUM"),
    ARCTURON("ARC"),
    SYLVANTERRA("SYL"),
    CELESTION("CLS"),
    VALERION("VLR"),
    IGNATIA("IGN"),
    AQUATERRA("AQT");

    private final String countryCode;

    private FictionalCountry(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public static FictionalCountry getCountryByCode(String code) {
        for (FictionalCountry country : FictionalCountry.values()) {
            if (country.getCountryCode().equals(code)) {
                return country;
            }
        }
        return null; // Return null if the code doesn't match any country
    }
}