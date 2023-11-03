abstract class Employee {
    private String userID;
    private String name;
    private String email;
    private String password;

    // attribute for collection of sessions

    // attribute for calendar

    public Employee(String userID, String name, String email, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUID() {
        return this.userID;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    // abstract ? getCourses(); need to figure out what type of data structure to use

    // abstract ? getSessions(); need to figure out what type of data structure to use

    abstract boolean addSession(Session session); // needs implementation of Session class

    abstract boolean removeSession(); // how to identify which session to remove?

    abstract boolean addCourse(Course course); // needs implementation of Course class

    abstract boolean removeCourse(); // how to identify which course to remove?

    abstract Calendar makeCalendar();

}
