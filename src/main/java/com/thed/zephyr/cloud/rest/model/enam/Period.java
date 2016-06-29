package com.thed.zephyr.cloud.rest.model.enam;

/**
 * Created by Kavya on 21-06-2016.
 */
public enum Period {
    HOURLY("hourly"),
    DAILY("daily"),
    MONTHLY("monthly"),
    YEARLY("yearly");

    public final String period;

    Period(String period) {
        this.period = period;
    }
}
