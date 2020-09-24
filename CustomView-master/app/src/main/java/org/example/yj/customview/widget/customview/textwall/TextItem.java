package org.example.yj.customview.widget.customview.textwall;

/**
 * @author yj on  2018/5/16 17:00
 *         邮箱 yj@allong.net
 * @version 1.0.0
 * <p>
 *     文字墙属性集合
 * </p>
 */

public class TextItem {
    /**
     * 文字
     */
    private String value;
    /**
     * 索引
     */
    private float index;
    /**
     * 文字大小
     */
    private int frontSize;
    /**
     * 文字颜色
     */
    private int frontColor;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public int getFrontSize() {
        return frontSize;
    }

    public void setFrontSize(int frontSize) {
        this.frontSize = frontSize;
    }

    public int getFrontColor() {
        return frontColor;
    }

    public void setFrontColor(int frontColor) {
        this.frontColor = frontColor;
    }
}
