package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

public interface EmployeeStatsProjection {

    BigDecimal getLeaveBalance();

    BigDecimal getCasualleaves();

    BigDecimal getSickleaves();

    BigDecimal getEarnedleaves();

    BigDecimal getRistrictedleaves();

    Long getActiveTasks();

    BigDecimal getBookingHrs();

    Long getPendingTickets();

    Long getClientInteraction();

    BigDecimal getTargethours();

    BigDecimal getExtraHours();
}
