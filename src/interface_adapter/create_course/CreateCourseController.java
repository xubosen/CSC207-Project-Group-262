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
        CreateCourseInputData inputData = new CreateCourseInputData(courseCode, courseName, adminID);
        createCourseInteractor.createCourse(inputData);
    }
}
