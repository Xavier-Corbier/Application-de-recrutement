package com.jobvxs.pl.postgre;

import com.jobvxs.bl.Notification;
import com.jobvxs.bl.RatingCompany;
import com.jobvxs.pl.NotificationDAO;

import java.sql.*;
import java.util.ArrayList;

public class NotificationDAOPostGre implements NotificationDAO {

    Connection conn;

    /**
     * Constructor for the NotificationDAOPostGre class
     * @param conn
     * @throws SQLException
     */
    public NotificationDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Get all the notification of the user concerned by giving is email
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Notification> getAllNotifications(String email) throws Exception {
        ArrayList<Notification> notifList = new ArrayList<>();

        PreparedStatement pstmt = conn.prepareStatement("SELECT public.\"Notification\".\"idNotif\", \"title\", \"description\", \"isRead\" FROM public.\"Notification\" JOIN public.\"Notif_User\" ON  public.\"Notif_User\".\"idNotif\" = public.\"Notification\".\"idNotif\" WHERE \"email\" = ?");
        pstmt.setString(1, email);

        ResultSet res = pstmt.executeQuery();

        Notification notification;

        if(!res.next()){
            throw new Exception("Error: no notif Found");
        } else {
            do {
                notification = new Notification(
                        res.getInt("idNotif"),
                        res.getString("title"),
                        res.getString("description"),
                        res.getBoolean("isRead"));
                notifList.add(notification);
            } while(res.next());
            // while we have a row
        }

        return notifList;
    }

    /**
     * Get the number of new notifications of a user by giving is email
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public int getNumberNewNotifications(String email) throws Exception {
        ArrayList<Integer> idList = new ArrayList<>();

        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(idNotif) FROM public.\"Notif_User\" WHERE email=? ;");
        pstmt.setString(1, email);

        ResultSet res = pstmt.executeQuery();

        if(!res.next()){
            throw new Exception("Error: no notif_user Found");
        } else {
            do {
                Integer integer = res.getInt("idNotif");
                idList.add(integer);
            } while (res.next());
        }

        int nbNotif = 0;
        for(Integer n : idList){
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT COUNT(idNotif) AS total FROM public.\"Notification\" WHERE isRead=? ;");
            pstmt1.setBoolean(1, false);

            ResultSet res1 = pstmt1.executeQuery();

            nbNotif += res1.getInt("total");
        }

        return nbNotif;
    }

    /**
     * Create a new notification
     * @param email
     * @param title
     * @param desc
     * @param type
     * @throws Exception
     */
    @Override
    public void createNotification(String email, String title, String desc, Notification.EnumNotif type) throws Exception {
        Notification notif = new Notification( -1,title, desc, false);

        String requete2 = "INSERT INTO public.\"Notification\" VALUES (DEFAULT,?,?,?) RETURNING \"idNotif\";";

        PreparedStatement pstmt2 = conn.prepareStatement(requete2);

        pstmt2.setString(1, title);
        pstmt2.setString(2, desc);
        pstmt2.setBoolean(3, false);

        ResultSet res = pstmt2.executeQuery();

        int idRes = -1;

        if(!res.next()){
            throw new Exception("Error: no Rate id Found");
        } else {
            do {
                idRes = res.getInt("idNotif");
            } while (res.next());
        }

        String requete1 = "INSERT INTO public.\"Notif_User\" VALUES (?, ?) ;";
        PreparedStatement pstmt1 = conn.prepareStatement(requete1);
        pstmt1.setString(1, email);
        pstmt1.setInt(2, idRes);

        pstmt1.executeUpdate();
    }

    /**
     * Delete a notification of a user by giving is email
     * @param email
     * @param idNotif
     * @throws Exception
     */
    @Override
    public void deleteNotification(String email, int idNotif) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"Notif_User\" WHERE email = ? AND \"idNotif\" =? ");
        pstmt.setString(1, email);
        pstmt.setInt(2, idNotif);
        pstmt.executeUpdate();

        PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM public.\"Notification\" WHERE \"idNotif\" =? ");
        pstmt1.setInt(1, idNotif);
        pstmt1.executeUpdate();

    }

    /**
     * Set a notification as read
     * @param idNotif
     * @throws Exception
     */
    public void  setReadNotification(int idNotif) throws Exception{
        PreparedStatement pstmt = conn.prepareStatement("UPDATE public.\"Notification\" SET \"isRead\" = ? WHERE \"idNotif\" = ?");
        pstmt.setBoolean(1, true);
        pstmt.setInt(2, idNotif);

        pstmt.executeUpdate();
    }
}
