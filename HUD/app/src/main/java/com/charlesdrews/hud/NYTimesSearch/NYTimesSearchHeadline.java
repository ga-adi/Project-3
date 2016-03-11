package com.charlesdrews.hud.NYTimesSearch;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * Created by williamtygret on 3/7/16.
 */
public class NYTimesSearchHeadline {

    private String main;
    private String printHeadline;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The main
     */
    public String getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     *
     * @return
     * The printHeadline
     */
    public String getPrintHeadline() {
        return printHeadline;
    }

    /**
     *
     * @param printHeadline
     * The print_headline
     */
    public void setPrintHeadline(String printHeadline) {
        this.printHeadline = printHeadline;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
