package pl.portalstrzelecki.psback.dtoandmappers.dto.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class EventDTO {
    private Long id_event;
    private String name;
    private String description;
    private List<String> organizers;
    private int participants;
    private String range;
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

    public boolean notNull() {
        if (
            id_event == null ||
            name == null ||
            description  == null ||
            dateOfStart  == null ||
            dateOfEnd  == null ||
            hourOfEnd  == null ||
            hourOfStart  == null ||
            entryFee  == null
        ) {
            return false;
        } else {
            return true;
        }

    }


}
