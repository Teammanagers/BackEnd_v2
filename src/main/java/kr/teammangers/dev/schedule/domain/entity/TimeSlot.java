package kr.teammangers.dev.schedule.domain.entity;

import jakarta.persistence.*;
import kr.teammangers.dev.global.common.base.BaseField;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.schedule.domain.enums.DayOfWeek;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static kr.teammangers.dev.global.error.code.ErrorStatus.TIME_SLOT_BAD_REQUEST;
import static kr.teammangers.dev.schedule.util.TimeSlotBitUtils.SLOTS_PER_HOUR;
import static kr.teammangers.dev.schedule.util.TimeSlotBitUtils.createTimeSlot;

@Entity
@Getter
@Table(name = "time_slot")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn <> 'N'")
@SQLDelete(sql = "UPDATE time_slot SET use_yn = 'N' WHERE id = ?")
public class TimeSlot extends BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "daily_slots",
            joinColumns = @JoinColumn(name = "time_slot_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "time_bits")
    private Map<DayOfWeek, Long> dailySlots;

    @Column(name = "is_config", nullable = false)
    private Boolean isConfigured;

    @Builder
    protected TimeSlot() {
        this.dailySlots = new EnumMap<>(DayOfWeek.class);
        this.isConfigured = false;
        Arrays.stream(DayOfWeek.values()).forEach(day -> dailySlots.put(day, 0L));
    }

    public void setTimeSlot(DayOfWeek day, int startHour, int startMinute, int endHour, int endMinute) {
        Long bitMask = createTimeSlot(startHour, startMinute, endHour, endMinute);
        dailySlots.merge(day, bitMask, (existing, newValue) -> existing | newValue);
    }

    public void update(TimeSlot schedule) {
        this.dailySlots = new HashMap<>();
        this.dailySlots.putAll(schedule.dailySlots);
    }

    public void updateConfig() {
        this.isConfigured = true;
    }

    @PrePersist
    @PreUpdate
    private void validateTimeSlots() {
        dailySlots.values().forEach(bits -> {
            if (Long.bitCount(bits) > 24 * SLOTS_PER_HOUR) {
                throw new GeneralException(TIME_SLOT_BAD_REQUEST);
            }
        });
    }


}
