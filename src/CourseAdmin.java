public class CourseAdmin extends Employee{

    public CourseAdmin(String userID, String name, String email, String password) {
        super(userID, name, email, password);
    }
    public boolean addSession(Session session) {
        return true;
    } // needs implementation of Session class

    public boolean removeSession() {
        return true;
    } // how to identify which session to remove?

    public boolean addCourse(Course course) {
        return true;
    } // needs implementation of Course class

    public boolean removeCourse() {
        return true;
    } // how to identify which course to remove?

    public Calendar makeCalendar() {
        throw new UnsupportedOperationException("Not supported yet."); // not implemented yet
    }
}
