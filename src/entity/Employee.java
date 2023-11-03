package entity;

import jdk.vm.ci.meta.Local;

import java.util.Calendar;
import java.util.Collection;
import java.time.LocalDateTime

public interface Employee {
    String getUserID();
    String getName();
    String getEmail();
    String getPassword();
    Collection getMySessions();
    LocalDateTime getlastTimeGotPayed();
    Calendar getCalendar();
    Collection getCourse();
    void makeCalendar();
}