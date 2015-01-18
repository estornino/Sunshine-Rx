package com.blokura.sunshine.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the forecast entries for this sample. It is fake data used locally for testing
 * purpose.
 */
public class LocalForecastEntry {

    private List<String> forecastCatalog;

    /**
     * Default constructor. All this hardcoded information is going to be used as mocked
     * information for the app.
     */
    public LocalForecastEntry() {
        forecastCatalog = new ArrayList<>();
        forecastCatalog.add("Today - Sunny - 88/63");
        forecastCatalog.add("Tomorrow - Foggy - 70/40");
        forecastCatalog.add("Weds - Cloudy - 72/63");
        forecastCatalog.add("Thurs - Asteroids - 75/65");
        forecastCatalog.add("Fri - Heavy Rain - 65/65");
        forecastCatalog.add("Sat - Help trapped - 60/51");
        forecastCatalog.add("Sun - Sunny - 80/68");
    }

    /**
     * We should return a full clone of TvShow objects inside catalog because all this data is in
     * memory and anyone can change it if we don't return copies.
     *
     * @return all forecast entries.
     */
    public List<String> getForecastCatalog() {
        return new ArrayList<>(forecastCatalog);
    }

}
