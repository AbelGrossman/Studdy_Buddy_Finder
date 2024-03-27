package fr.pantheonsorbonne.cri.ModelFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/study_buddy_finder";
        private static final String DB_USERNAME = "root";
        private static final String DB_PASSWORD = "root";

        private DatabaseConnection() {
                throw new IllegalStateException("Utility class");
        }

        public static void dataBaseConnect() {

                try {
                        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                        Statement statement = connection.createStatement();
                        createDatabase(statement);
                        connection.close();
                } catch (Exception e) {
                        System.out.println(e);
                }
        }

        private static void createDatabase(Statement statement) throws Exception {
                String createUserTableQuery = "CREATE TABLE IF NOT EXISTS User ("
                                + "user_id INT PRIMARY KEY AUTO_INCREMENT,"
                                + "first_name VARCHAR(100),"
                                + "last_name VARCHAR(100),"
                                + "user_name VARCHAR(50) UNIQUE,"
                                + "user_email VARCHAR(100) UNIQUE,"
                                + "user_password VARCHAR(50),"
                                + "location_1 VARCHAR(100),"
                                + "location_2 VARCHAR(100),"
                                + "interest_1 VARCHAR(100),"
                                + "interest_2 VARCHAR(100),"
                                + "user_studies VARCHAR(100)"
                                + ")";
                String createGroupTableQuery = "CREATE TABLE IF NOT EXISTS `Group` ("
                                + "group_id INT PRIMARY KEY AUTO_INCREMENT,"
                                + "group_name VARCHAR(100),"
                                + "study_domain VARCHAR(100),"
                                + "study_level VARCHAR(100),"
                                + "admin_id INT,"
                                + "FOREIGN KEY (admin_id) REFERENCES User(user_id)"
                                + ")";
                String createGroupMembersTableQuery = "CREATE TABLE IF NOT EXISTS GroupMembers ("
                                + "group_id INT,"
                                + "user_id INT,"
                                + "FOREIGN KEY (group_id) REFERENCES `Group`(group_id),"
                                + "FOREIGN KEY (user_id) REFERENCES User(user_id),"
                                + "PRIMARY KEY (group_id, user_id)"
                                + ")";
                String createStuddyBuddiesTableQuery = "CREATE TABLE IF NOT EXISTS StuddyBuddies ("
                                + "user_id INT,"
                                + "studdy_buddy_id INT,"
                                + "FOREIGN KEY (user_id) REFERENCES User(user_id),"
                                + "FOREIGN KEY (studdy_buddy_id) REFERENCES User(user_id),"
                                + "PRIMARY KEY (user_id, studdy_buddy_id)"
                                + ")";
                String createMeetingTableQuery = "CREATE TABLE IF NOT EXISTS Meeting ("
                                + "meeting_id INT PRIMARY KEY AUTO_INCREMENT,"
                                + "meeting_name VARCHAR(100),"
                                + "group_id INT,"
                                + "meeting_date DATE,"
                                + "meeting_start_time TIME,"
                                + "meeting_end_time TIME,"
                                + "meeting_location VARCHAR(100),"
                                + "amount_of_participants INT,"
                                + "reservation_required BOOLEAN"
                                + "meeting_admin INT,"
                                + ")";
                String createMeetingParticipantsTableQuery = "CREATE TABLE IF NOT EXISTS MeetingParticipants ("
                                + "meeting_id INT,"
                                + "user_id INT,"
                                + "FOREIGN KEY (meeting_id) REFERENCES Meeting(meeting_id),"
                                + "FOREIGN KEY (user_id) REFERENCES User(user_id),"
                                + "PRIMARY KEY (meeting_id, user_id)"
                                + ")";
                String createFriendRequestTableQuery = "CREATE TABLE IF NOT EXISTS FriendRequest ("
                                + "sender_id INT,"
                                + "receiver_id INT,"
                                + "FOREIGN KEY (sender_id) REFERENCES User(user_id),"
                                + "FOREIGN KEY (receiver_id) REFERENCES User(user_id),"
                                + "PRIMARY KEY (sender_id, receiver_id)"
                                + ")";
                String createGroupRequestTableQuery = "CREATE TABLE IF NOT EXISTS GroupRequest ("
                                + "sender_id INT,"
                                + "receiver_id INT,"
                                + "group_id INT,"
                                + "FOREIGN KEY (sender_id) REFERENCES User(user_id),"
                                + "FOREIGN KEY (receiver_id) REFERENCES User(user_id),"
                                + "FOREIGN KEY (group_id) REFERENCES `Group`(group_id),"
                                + "PRIMARY KEY (sender_id, receiver_id, group_id)"
                                + ")";
                String createAdminRequestsTableQuery = "CREATE TABLE IF NOT EXISTS AdminRequests ("
                                + "admin_id INT,"
                                + "sender_id INT,"
                                + "group_id INT,"
                                + "FOREIGN KEY (group_id) REFERENCES `Group`(group_id),"
                                + "FOREIGN KEY (admin_id) REFERENCES User(user_id),"
                                + "FOREIGN KEY (sender_id) REFERENCES User(user_id),"
                                + "PRIMARY KEY (admin_id, sender_id, group_id)"
                                + ")";
                statement.executeUpdate(createUserTableQuery);
                statement.executeUpdate(createGroupTableQuery);
                statement.executeUpdate(createGroupMembersTableQuery);
                statement.executeUpdate(createStuddyBuddiesTableQuery);
                statement.executeUpdate(createMeetingTableQuery);
                statement.executeUpdate(createMeetingParticipantsTableQuery);
                statement.executeUpdate(createFriendRequestTableQuery);
                statement.executeUpdate(createGroupRequestTableQuery);
                statement.executeUpdate(createAdminRequestsTableQuery);
        }
}
