package use_case;

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
