package kr.teammangers.dev.schedule.util;

import kr.teammangers.dev.global.common.payload.exception.GeneralException;
import kr.teammangers.dev.schedule.dto.TimeRangeDto;
import kr.teammangers.dev.schedule.dto.TimeSlotDto;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus.*;

@UtilityClass
public class TimeSlotBitUtils {

    public static final int SLOTS_PER_HOUR = 2;
    public static final int TOTAL_SLOTS = 24 * SLOTS_PER_HOUR;

    public static Long createTimeSlot(int startHour, int startMinute, int endHour, int endMinute) {
        validateTimeRange(startHour, startMinute, endHour, endMinute);
        int startSlot = calculateSlot(startHour, startMinute);
        int endSlot = calculateSlot(endHour, endMinute);
        return createBitMask(startSlot, endSlot);
    }

    public static Long createBitMask(int startSlot, int endSlot) {
        if (startSlot >= endSlot) {
            throw new GeneralException(TIME_SLOT_ORDER);
        }
        long mask = 0L;
        for (int i = startSlot; i <= endSlot; i++) {
            mask |= (1L << i);
        }
        return mask;
    }

    public static int calculateSlot(int hour, int minute) {
        return hour * SLOTS_PER_HOUR + (minute / (60 / SLOTS_PER_HOUR));
    }

    public static String toBinaryString(Long value) {
        return leftPad(Long.toBinaryString(value));
    }

    public static List<TimeRangeDto> getTimeRangeDtoList(Long bits) {
        List<TimeRangeDto> ranges = new ArrayList<>();
        int start = -1;

        for (int i = 0; i < TOTAL_SLOTS; i++) {
            boolean isSet = (bits & (1L << i)) != 0;
            if (isSet && start == -1) {
                start = i;
            } else if (!isSet && start != -1) {
                ranges.add(createTimeRange(start, i));
                start = -1;
            }
        }
        if (start != -1) {
            ranges.add(createTimeRange(start, TOTAL_SLOTS));
        }
        return ranges;
    }

    public static TimeSlotDto calculateCommonTimeSlot(List<TimeSlotDto> timeSlotDtoList) {


        return null;
    }

    private static TimeRangeDto createTimeRange(int startSlot, int endSlot) {
        LocalTime start = LocalTime.of(startSlot / SLOTS_PER_HOUR, (startSlot % SLOTS_PER_HOUR) * (60 / SLOTS_PER_HOUR));
        LocalTime end = LocalTime.of(endSlot / SLOTS_PER_HOUR, (endSlot % SLOTS_PER_HOUR) * (60 / SLOTS_PER_HOUR));
        return TimeRangeDto.builder()
                .startTime(start)
                .endTime(end)
                .build();
    }

    private static String leftPad(String str) {
        if (str.length() >= SLOTS_PER_HOUR) return str;
        StringBuilder sb = new StringBuilder();
        while (sb.length() < SLOTS_PER_HOUR - str.length()) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }


    private static void validateTimeRange(int startHour, int startMinute, int endHour, int endMinute) {
        if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23) {
            throw new GeneralException(TIME_SLOT_HOUR);
        }
        if (startMinute < 0 || startMinute > 59 || endMinute < 0 || endMinute > 59) {
            throw new GeneralException(TIME_SLOT_MINUTE);
        }
        if (startMinute % (60 / SLOTS_PER_HOUR) != 0 || endMinute % (60 / SLOTS_PER_HOUR) != 0) {
            throw new GeneralException(TIME_SLOT_INTERVAL);
        }
    }

}
