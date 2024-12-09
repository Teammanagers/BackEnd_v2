package kr.teammangers.dev.schedule.domain;

import jakarta.persistence.*;
import kr.teammangers.dev.common.entity.BaseField;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.schedule.enums.DayOfWeek;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus.TIME_SLOT_BAD_REQUEST;
import static kr.teammangers.dev.schedule.util.TimeSlotBitUtils.SLOTS_PER_HOUR;
import static kr.teammangers.dev.schedule.util.TimeSlotBitUtils.createTimeSlot;

@Entity
@Getter
@Table(name = "time_slot")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Map<DayOfWeek, Long> dailySlots = new EnumMap<>(DayOfWeek.class);

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Builder
    public TimeSlot(Long memberId) {
        this.dailySlots = new EnumMap<>(DayOfWeek.class);
        this.memberId = memberId;
        Arrays.stream(DayOfWeek.values()).forEach(day -> dailySlots.put(day, 0L));
    }

    public void setTimeSlot(DayOfWeek day, int startHour, int startMinute, int endHour, int endMinute) {
        Long bitMask = createTimeSlot(startHour, startMinute, endHour, endMinute);
        dailySlots.merge(day, bitMask, (existing, newValue) -> existing | newValue);
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
