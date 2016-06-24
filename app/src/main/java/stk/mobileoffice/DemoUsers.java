package stk.mobileoffice;

/**
 * Author: stk
 * Date: 2016/6/24
 * Time: 22:35
 */
public enum DemoUsers {
    Admin(100, "q", "q");

    private int id;
    private String username;
    private String password;
    DemoUsers(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static boolean login(String name, String pw) {
        for (DemoUsers i : DemoUsers.values()) {
            if (name.equals(i.username) && pw.equals(i.password)) {
                CurrentUser.id = i.id;
                return true;
            }
        }
        return false;
    }
}
