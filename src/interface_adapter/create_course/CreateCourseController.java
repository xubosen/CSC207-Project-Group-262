package interface_adapter.create_course;

import use_case.create_course.CreateCourseInputBoundary;
import use_case.create_course.CreateCourseInputData;

public class CreateCourseController {
    private CreateCourseInputBoundary createCourseInteractor;


    public CreateCourseController(CreateCourseInputBoundary createCourseInteractor) {
        this.createCourseInteractor = createCourseInteractor;
    }

    /**
     * Creates input data after taking inputs courseCode, courseName and adminID.
     * @param courseCode The created course's code
     * @param courseName The created course's name
     * @param adminID The created course's adminID
     */
    public void createCourse(String courseCode, String courseName, String adminID) {
        // TODO: Check the information below with others. AdminID would not be required because it would take the
        //  user that is logged in
        // May or may not need employee parameter, it should be logged in state thing.
        // The adminID might not be required because it would just check logged in state potentially.
        CreateCourseInputData inputData = new CreateCourseInputData(courseCode, courseName, adminID);
        createCourseInteractor.createCourse(inputData);
    }
}
