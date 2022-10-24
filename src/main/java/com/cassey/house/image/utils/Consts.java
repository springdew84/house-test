package com.cassey.house.image.utils;

import java.util.regex.Pattern;

public class Consts {
    public static double QUALITY_DEFAULT = 0.85F;
    public static double QUALITY_LOW = 0.70F;
    public static double QUALITY_LOW_LOW = 0.60F;
    public static double QUALITY_HIGH = 0.95F;


    /**
     * favicon_ico URI
     */
    public static final String FAVICON_ICO = "/favicon.ico";

    /**
     * 缩略图目录
     */
    public static final String ORIGIN_DIR = "OriginalImage";

    /**
     * 预裁剪参数值
     */
    public static final String PRE_RESIZE = "pre_resize";

    /**
     * 支持的image类型
     */
    public static final String[] SUPPORT_IMAGE = {".jpg", ".jpeg", ".gif", ".png", ".bmp", ".webp"};

    /**
     * 最大尺寸
     */
    public static final int IMAGE_MAX_SIZE = 2000;

    /**
     * 图片请求格式
     */
    public static final Pattern IMAGE_PATTERN = Pattern.compile("_[\\d]+_[\\d]+_[\\d]+_(.*)\\.([^\\.]*)$", Pattern.CASE_INSENSITIVE);

    /**
     * png
     */
    public static final String IMGE_TYPE_PNG = "png";

    /**
     * jpg
     */
    public static final String IMGE_TYPE_JPG = "jpg";

    /**
     * webp mine type
     */
    public static final String WEBP_MINE_TYPE = "image/webp";

    /**
     * bmp
     */
    public static final String IMGE_TYPE_BMP = "bmp";

    /**
     * gif
     */
    public static final String IMGE_TYPE_GIF = "gif";

    /**
     * 水印透明度参数
     */
    public static final float WATER_MARK_OPACITY = 0.99F;

    /**
     * 文字水印透明度参数
     */
    public static final float WATER_MARK_TEXT_OPACITY = 0.6F;
}
