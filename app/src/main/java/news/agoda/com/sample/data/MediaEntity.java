package news.agoda.com.sample.data;

/**
 * This class represents a media item
 */
public class MediaEntity {
    private String url;
    private String format;
    private int height;
    private int width;
    private String type;
    private String subtype;
    private String caption;
    private String copyright;

    public MediaEntity() {
    }

    public MediaEntity(String url, String format, int height, int width, String type,
                       String subtype, String caption, String copyright) {
        this.url = url;
        this.format = format;
        this.height = height;
        this.width = width;
        this.type = type;
        this.subtype = subtype;
        this.caption = caption;
        this.copyright = copyright;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
