package pl.portalstrzelecki.psback.dtoandmappers.dto.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class EventRegistrationDTO {

    private Long id_event;
    private String name;
    private String description;
    private String rangeName;
    private Long id_club;
    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private LocalTime hourOfStart;
    private LocalTime hourOfEnd;
    private Long entryFee;

    private boolean membersOnly;
    private boolean openEntry;
    private boolean competition;
    private boolean practice;
    private boolean course;

    public boolean notNull() {
        return !(id_event == null ||
                name == null ||
                description  == null ||
                dateOfStart  == null ||
                dateOfEnd  == null ||
                hourOfEnd  == null ||
                hourOfStart  == null ||
                entryFee  == null ||
                id_club == null ||
                rangeName == null) ;
    }

}
