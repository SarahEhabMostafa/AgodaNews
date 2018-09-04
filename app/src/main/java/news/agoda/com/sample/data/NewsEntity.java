package news.agoda.com.sample.data;

import java.util.List;

/**
 * This represents a news item
 */
public class NewsEntity {
    private static final String TAG = NewsEntity.class.getSimpleName();
    private String section;
    private String subsection;
    private String title;
    private String summary;
    private String url;
    private String byline;
    private String item_type;
    private String updated_date;
    private String created_date;
    private String published_date;
    private String material_type_facet;
    private String kicker;
    private String[] des_facet;
    private String[] org_facet;
    private String[] per_facet;
    private String[] geo_facet;
    private List<MediaEntity> multimedia;

    public NewsEntity() {
    }

    public NewsEntity(String section, String subsection, String title, String summary, String url,
                      String byline, String item_type, String updated_date, String created_date,
                      String published_date, String material_type_facet, String kicker,
                      String[] des_facet, String[] org_facet, String[] per_facet,
                      String[] geo_facet, List<MediaEntity> multimedia) {
        this.section = section;
        this.subsection = subsection;
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.byline = byline;
        this.item_type = item_type;
        this.updated_date = updated_date;
        this.created_date = created_date;
        this.published_date = published_date;
        this.material_type_facet = material_type_facet;
        this.kicker = kicker;
        this.des_facet = des_facet;
        this.org_facet = org_facet;
        this.per_facet = per_facet;
        this.geo_facet = geo_facet;
        this.multimedia = multimedia;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getMaterial_type_facet() {
        return material_type_facet;
    }

    public void setMaterial_type_facet(String material_type_facet) {
        this.material_type_facet = material_type_facet;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public String[] getDes_facet() {
        return des_facet;
    }

    public void setDes_facet(String[] des_facet) {
        this.des_facet = des_facet;
    }

    public String[] getOrg_facet() {
        return org_facet;
    }

    public void setOrg_facet(String[] org_facet) {
        this.org_facet = org_facet;
    }

    public String[] getPer_facet() {
        return per_facet;
    }

    public void setPer_facet(String[] per_facet) {
        this.per_facet = per_facet;
    }

    public String[] getGeo_facet() {
        return geo_facet;
    }

    public void setGeo_facet(String[] geo_facet) {
        this.geo_facet = geo_facet;
    }

    public List<MediaEntity> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MediaEntity> mediaEntityList) {
        this.multimedia = mediaEntityList;
    }
}
