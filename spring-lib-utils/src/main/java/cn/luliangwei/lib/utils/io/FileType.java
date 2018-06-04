/*
 * Copyright © 2017 signit.cn. All rights reserved.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package cn.luliangwei.lib.utils.io;

/**
 * 基于文件头描述源文件真实类型.
 * </p>
 *
 * @author zhd
 * @since 1.0.0
 */
public enum FileType {

    /**
     * JPEG.
     */
    JPEG("FFD8FF"),

    /**
     * JPG.
     */
    JPG("FFD8FF"),

    /**
     * JPE.
     */
    JPE("FFD8FF"),

    /**
     * PNG.
     */
    PNG("89504E47"),

    /**
     * GIF.
     */
    GIF("47494638"),

    /**
     * TIFF.
     */
    TIFF("49492A00"),

    /**
     * Windows Bitmap.
     */
    BMP("424D"),

    /**
     * CAD.
     */
    DWG("41433130"),

    /**
     * Adobe Photoshop.
     */
    PSD("38425053"),

    /**
     * Rich Text Format.
     */
    RTF("7B5C727466"),

    /**
     * XML.
     */
    XML("3C3F786D6C"),

    /**
     * HTML.
     */
    HTML("68746D6C3E"),

    /**
     * Email [thorough only].
     */
    EML("44656C69766572792D646174653A"),

    /**
     * Outlook Express.
     */
    DBX("CFAD12FEC5FD746F"),

    /**
     * Outlook (pst).
     */
    PST("2142444E"),

    /**
     * MS 2003 Word/Excel/PointPower.
     */
    MS_2003("D0CF11E0"),

    /**
     * MS 2007 and later Archive.MS Word/Excel/PointPower. （docx,pptx,xlsx）
     */
    MS_2007("504B0304"),

    /**
     * MS Access.
     */
    MDB("5374616E64617264204A"),

    /**
     * WordPerfect.
     */
    WPD("FF575043"),

    /**
     * Postscript.
     */
    EPS("252150532D41646F6265"),

    /**
     * Adobe Acrobat.
     */
    PDF("255044462D312E"),

    /**
     * Quicken.
     */
    QDF("AC9EBD8F"),

    /**
     * Windows Password.
     */
    PWL("E3828596"),

    /**
     * ZIP Archive. （docx,pptx,xlsx）
     */
    ZIP("504B0304"),

    /**
     * RAR Archive.
     */
    RAR("52617221"),

    /**
     * Wave.
     */
    WAV("57415645"),

    /**
     * AVI.
     */
    AVI("41564920"),

    /**
     * Real Audio.
     */
    RAM("2E7261FD"),

    /**
     * Real Media.
     */
    RM("2E524D46"),

    /**
     * MPEG (mpg).
     */
    MPG("000001BA"),

    /**
     * Quicktime.
     */
    MOV("6D6F6F76"),

    /**
     * Windows Media.
     */
    ASF("3026B2758E66CF11"),

    /**
     * MIDI.
     */
    MID("4D546864"),

    /**
     * UNKNOWN
     */
    UNKNOWN("");

    private String fileHeader = "";

    FileType(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    /**
     * 获取当前枚举对应的文件头的16进制表示字符串值.
     *
     * @return 16进制表示字符串值
     * @author zhd
     * @since 1.0.0
     */
    public String fileHeader() {
        return fileHeader;
    }
}
