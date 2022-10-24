package com.cassey.house.image.utils;

import net.coobird.thumbnailator.geometry.Positions;

/**
 * 水印贴图
 */
public enum CornerMark {
    MARK_TYPE_30(30, "./waterImage/exclusive-en.png", Positions.TOP_LEFT), // 独家英文水印
    MARK_TYPE_31(31, "./waterImage/exclusive-cn.png", Positions.TOP_LEFT), // 独家中文水印
    MARK_TYPE_32(32, "./waterImage/exclusive-en_2.png", Positions.TOP_LEFT), // 独家英文水印2倍
    MARK_TYPE_33(33, "./waterImage/exclusive-cn_2.png", Positions.TOP_LEFT), // 独家中文水印2倍
    MARK_TYPE_34(34, "./waterImage/exclusive-en_3.png", Positions.TOP_LEFT), // 独家英文水印3倍
    MARK_TYPE_35(35, "./waterImage/exclusive-cn_3.png", Positions.TOP_LEFT), // 独家中文水印3倍
    MARK_TYPE_36(36, "./waterImage/exclusive-en_4.png", Positions.TOP_LEFT), // 独家英文水印4倍
    MARK_TYPE_37(37, "./waterImage/exclusive-cn_4.png", Positions.TOP_LEFT), // 独家中文水印4倍
    MARK_TYPE_46(46, "./waterImage/oneday-en.png", Positions.TOP_LEFT), // 只一天英文水印
    MARK_TYPE_47(47, "./waterImage/oneday-cn.png", Positions.TOP_LEFT), // 只一天中文水印
    MARK_TYPE_48(48, "./waterImage/oneday-en_2.png", Positions.TOP_LEFT), // 只一天英文水印2倍
    MARK_TYPE_49(49, "./waterImage/oneday-cn_2.png", Positions.TOP_LEFT), // 只一天中文水印2倍
    MARK_TYPE_50(50, "./waterImage/oneday-en_3.png", Positions.TOP_LEFT), // 只一天英文水印3倍
    MARK_TYPE_51(51, "./waterImage/oneday-cn_3.png", Positions.TOP_LEFT), // 只一天中文水印3倍
    MARK_TYPE_52(52, "./waterImage/oneday-en_4.png", Positions.TOP_LEFT), // 只一天英文水印4倍
    MARK_TYPE_53(53, "./waterImage/oneday-cn_4.png", Positions.TOP_LEFT), // 只一天中文水印4倍
    MARK_TYPE_40(40, "./waterImage/watermark-cn.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_41(41, "./waterImage/watermark-en.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_42(42, "./waterImage/watermark-ca-cn.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_43(43, "./waterImage/watermark-au-cn.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_44(44, "./waterImage/watermark-uk-cn.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_45(45, "./waterImage/watermark-mini.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_54(54, "./waterImage/watermark-fr-cn.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_55(55, "./waterImage/watermark-de-cn.png", Positions.BOTTOM_RIGHT),
    MARK_TYPE_56(56, "./waterImage/exclusive-en_2.png", Positions.TOP_LEFT), // 独家英文水印2倍
    MARK_TYPE_57(57, "./waterImage/exclusive-cn_2.png", Positions.TOP_LEFT), // 独家中文水印2倍
    MARK_TYPE_58(58, "./waterImage/oneday-en_2.png", Positions.TOP_LEFT), // 只一天英文水印2倍
    MARK_TYPE_59(59, "./waterImage/oneday-cn_2.png", Positions.TOP_LEFT); // 只一天中文水印2倍

    private int type;
    private String fileName;
    private Positions position;

    CornerMark(int type, String fileName, Positions position) {
        this.type = type;
        this.fileName = fileName;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public Positions getPosition() {
        return position;
    }

    public static CornerMark getByType(int type) {
        for (CornerMark cornerMark : CornerMark.values()) {
            if (cornerMark.getType() == type) {
                return cornerMark;
            }
        }

        return null;
    }
}
