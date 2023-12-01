package use_case.remove_from_course;

public class RemoveFromCourseInputData {
    private String employeeID;
    private String courseCode;

    public RemoveFromCourseInputData(String employeeID, String courseCode) {
        this.employeeID = employeeID;
        this.courseCode = courseCode;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
