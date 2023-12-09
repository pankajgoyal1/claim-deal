package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.models.enums.DealState;

import java.time.LocalDateTime;
import java.util.List;

public class Deal {
    private String id;
    private String name;
    private DealState state;
    private int quantity;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DealState getState() {
        return state;
    }

    public void setState(DealState state) {
        this.state = state;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public LocalDateTime getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(LocalDateTime endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public boolean isEndedBefore() {
        return isEndedBefore;
    }

    public void setEndedBefore(boolean endedBefore) {
        isEndedBefore = endedBefore;
    }

    public List<String> getUsersAlreadyBought() {
        return usersAlreadyBought;
    }

    public void setUsersAlreadyBought(List<String> usersAlreadyBought) {
        this.usersAlreadyBought = usersAlreadyBought;
    }

    private int hours;
    private LocalDateTime endTimeStamp;
    private boolean isEndedBefore;
    private List<String> usersAlreadyBought;
}
