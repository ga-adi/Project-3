package com.charlesdrews.hud.NYTimesSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by williamtygret on 3/7/16.
 */
public class NYTimesSearchResponse {

    private NYTimesSearchMeta meta;
    private List<NYTimesSearchDoc> docs = new ArrayList<NYTimesSearchDoc>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The meta
     */
    public NYTimesSearchMeta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(NYTimesSearchMeta meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     * The docs
     */
    public List<NYTimesSearchDoc> getDocs() {
        return docs;
    }

    /**
     *
     * @param docs
     * The docs
     */
    public void setDocs(List<NYTimesSearchDoc> docs) {
        this.docs = docs;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
