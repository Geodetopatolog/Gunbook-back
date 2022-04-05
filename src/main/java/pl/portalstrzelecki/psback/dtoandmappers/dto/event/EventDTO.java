package pl.portalstrzelecki.psback.dtoandmappers.dto.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDTO {
    private Long id_event;
    private String name;
    private String description;
    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private LocalTime hourOfStart;
    private LocalTime hourOfEnd;
    private Long entryFee;
    private boolean membersOnly;
    private boolean openEntry;
    private boolean isCompetition;
    private boolean isPractice;
    private boolean isCourse;

}
