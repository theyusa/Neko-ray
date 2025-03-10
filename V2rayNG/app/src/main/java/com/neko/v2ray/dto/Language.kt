package com.neko.v2ray.dto

enum class Language(val code: String) {
    AUTO("auto"),
    ENGLISH("en"),
    CHINA("zh-rCN"),
    TRADITIONAL_CHINESE("zh-rTW"),
    VIETNAMESE("vi"),
    RUSSIAN("ru"),
    PERSIAN("fa"),
    ARABIC("ar"),
    BANGLA("bn"),
    BAKHTIARI("bqi-rIR"),
    TURKISH("tr");

    companion object {
        fun fromCode(code: String): Language {
            return entries.find { it.code == code } ?: AUTO
        }
    }
}
