package data_access.in_memory_dao;

import entity.Leave;

import java.util.HashMap;
import java.util.Map;

public class InMemoryLeaveDataAccessObject {
    private Map<String, Leave> leaves;

    public InMemoryLeaveDataAccessObject() {
        leaves = new HashMap<>();
    }

    public InMemoryLeaveDataAccessObject(HashMap<String, Leave> leaves) {
        this.leaves = new HashMap<>(leaves);
    }

    public void save(Leave leave) {
        this.leaves.put(leave.getLeaveID(), leave);
    }

    public void addLeave(Leave leave) {
        this.leaves.put(leave.getLeaveID(), leave);
    }

    public boolean existsByID(String leaveID) {
        return leaves.containsKey(leaveID);
    }

    public Leave getByID(String leaveID) {
        return leaves.get(leaveID);
    }

    public boolean removeLeave(Leave leave) {
        if (existsByID(leave.getLeaveID())) {
            this.leaves.remove(leave.getLeaveID());
            return true;
        }
        return false;
    }
}
