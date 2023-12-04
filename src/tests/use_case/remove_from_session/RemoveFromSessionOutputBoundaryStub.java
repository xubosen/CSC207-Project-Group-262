package tests.use_case.remove_from_session;

import use_case.remove_from_session.RemoveFromSessionOutputBoundary;
import use_case.remove_from_session.RemoveFromSessionOutputData;

class RemoveFromSessionOutputBoundaryStub implements RemoveFromSessionOutputBoundary {
    private RemoveFromSessionOutputData outputData;

    @Override
    public void prepareView(RemoveFromSessionOutputData outputData) {
        this.outputData = outputData;
    }

    public RemoveFromSessionOutputData getOutputData() {
        return outputData;
    }
}
