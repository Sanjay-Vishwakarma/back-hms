package com.learn.jd.dto;

import java.math.BigDecimal;

public class RevenueSummaryResponse {

    private long totalBookings;
    private long activeBookings;
    private long cancelledBookings;
    private BigDecimal confirmedRevenue;

    public RevenueSummaryResponse(long totalBookings, long activeBookings, long cancelledBookings, BigDecimal confirmedRevenue) {
        this.totalBookings = totalBookings;
        this.activeBookings = activeBookings;
        this.cancelledBookings = cancelledBookings;
        this.confirmedRevenue = confirmedRevenue;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public long getActiveBookings() {
        return activeBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public BigDecimal getConfirmedRevenue() {
        return confirmedRevenue;
    }
}
