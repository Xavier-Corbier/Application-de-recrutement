package com.jobvxs.bl;

public class Notification {

    /**
     * Enum for the type of Notification
     */
    public enum EnumNotif{
        CLASSIC, REPORT, JOB
    }

    private int id;
    private String title;
    private String description;
    private boolean isRead;

    /**
     * Constructor for the Notification class
     * @param id
     * @param title
     * @param description
     * @param isRead
     */
    public Notification(int id, String title, String description, boolean isRead){
        this.id = id;
        this.title = title;
        this.description = description;
        this.isRead = isRead;
    }

    /**
     * Getter for the id of the notification
     * @return
     */
    public int getId() { return id; }

    /**
     * Getter for the title of a notification
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the description of a notification
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the isRead of a notification
     * @return
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Setter for the id of a notification
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for the title of a notification
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for the description of a notification
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for the isRead of a notification
     * @param isRead
     */
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
