package com.metoo.core.domain.merchant;

/**
 * 特点
 * User: Zhang xiaomei
 * Date: 2016/12/6
 *
 * 网页表现为：
 * <span class="feature feature-wifi" title="WIFI"></span>
 * <span class="feature feature-wifi-free" title="免费WIFI"></span>
 * <span class="feature feature-parking" title="停车场"></span>
 * <span class="feature feature-parking-free" title="免费停车场"></span>
 * <span class="feature feature-broadband" title="有线宽带"></span>
 * <span class="feature feature-broadband-free" title="免费有线宽带"></span>
 * <span class="feature feature-restaurant" title="餐厅"></span>
 * <span class="feature feature-pet" title="宠物"></span>
 * <span class="feature feature-gymnasium" title="健身房"></span>
 * <span class="feature feature-meeting" title="会议"></span>
 * <span class="feature feature-airport" title="接机服务"></span>
 * <span class="feature feature-swimming" title="游泳"></span>
 */
public enum Feature {
    WIFI("WIFI", 1),
    WIFI_FREE("免费WIFI", 1 << 1),
    PARKING("停车场", 1 << 2),
    PARKING_FREE("免费停车场", 1 << 3),
    BROADBAND("有线宽带", 1 << 4),
    BROADBAND_FREE("免费有线宽带", 1 << 5),
    RESTAURANT("餐厅", 1 << 6),
    PET("宠物", 1 << 7),
    GYMNASIUM("健身房", 1 << 8),
    MEETING("会议", 1 << 9),
    AIRPORT("接机服务", 1 << 10),
    SWIMMING("游泳", 1 << 11),
    ;


    public static final String HTML_TPL = "<span class=\"%s\" title=\"%s\"></span>";
    private int val;
    private String label;

    Feature(String label, int val) {
        this.val = val;
        this.label = label;
    }

    public String getValue() {
        return name();
    }

    public int getVal() {
        return val;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return "feature feature-" + this.getValue().replace("_", "-").toLowerCase();
    }

    public String getHtml() {
        return String.format(HTML_TPL, getClassName(), getLabel());
    }

    public static void main(String[] args) {
        for (Feature feature : Feature.values()) {
            System.out.println(feature.getHtml());
        }
    }
}
