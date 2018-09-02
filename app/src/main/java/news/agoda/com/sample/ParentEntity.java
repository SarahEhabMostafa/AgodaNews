package news.agoda.com.sample;

import java.util.List;

public class ParentEntity {
    private String status;
    private String copyright;
    private String section;
    private String last_updated;
    private int num_results;
    private List<NewsEntity> results;

    public ParentEntity() {
    }

    public ParentEntity(String status, String copyright, String section, String last_updated,
                        int num_results, List<NewsEntity> results) {
        this.status = status;
        this.copyright = copyright;
        this.section = section;
        this.last_updated = last_updated;
        this.num_results = num_results;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public int getNum_results() {
        return num_results;
    }

    public void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    public List<NewsEntity> getResults() {
        return results;
    }

    public void setResults(List<NewsEntity> results) {
        this.results = results;
    }
}
