package com.jobvxs.pl;

import com.jobvxs.bl.Notification;

import java.util.ArrayList;
import java.util.List;

public interface NotificationDAO {

    /**
     * Get all the notifications of a user by giving is email
     * @param email
     * @return
     * @throws Exception
     */
    public ArrayList<Notification> getAllNotifications(String email) throws Exception;

    /**
     * Get the number of new notifications of a User by giving is email
     * @param email
     * @return
     * @throws Exception
     */
    public int getNumberNewNotifications(String email) throws Exception;

    /**
     * Create a new notification
     * @param email
     * @param title
     * @param desc
     * @param type
     * @throws Exception
     */
    public void createNotification(String email, String title, String desc, Notification.EnumNotif type) throws Exception;

    /**
     * Delete a notification
     * @param email
     * @param idNotif
     * @throws Exception
     */
    public void deleteNotification(String email, int idNotif) throws Exception;

    /**
     * Set a notification on read
     * @param idNotif
     * @throws Exception
     */
    public void setReadNotification(int idNotif) throws Exception;
}
