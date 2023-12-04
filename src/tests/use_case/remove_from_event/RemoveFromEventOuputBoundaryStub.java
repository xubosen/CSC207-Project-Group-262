package tests.use_case.remove_from_event;

import use_case.remove_from_event.RemoveFromEventOutputBoundary;
import use_case.remove_from_event.RemoveFromEventOutputData;

class RemoveFromEventOutputBoundaryStub implements RemoveFromEventOutputBoundary {
    private RemoveFromEventOutputData outputData;

    @Override
    public void prepareView(RemoveFromEventOutputData outputData) {
        this.outputData = outputData;
    }

    public RemoveFromEventOutputData getOutputData() {
        return outputData;
    }
}
