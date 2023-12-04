package tests.use_case.remove_from_course;

import use_case.remove_from_course.RemoveFromCourseOutputBoundary;
import use_case.remove_from_course.RemoveFromCourseOutputData;

class RemoveFromCourseOutputBoundaryStub implements RemoveFromCourseOutputBoundary {
    private RemoveFromCourseOutputData outputData;

    @Override
    public void prepareView(RemoveFromCourseOutputData outputData) {
        this.outputData = outputData;
    }

    public RemoveFromCourseOutputData getOutputData() {
        return outputData;
    }
}
