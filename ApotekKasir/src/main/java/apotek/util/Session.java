package apotek.util;

import apotek.model.User;

/** Menyimpan pengguna yang sedang login. */
public final class Session {
    private static User current;

    private Session() { }

    public static User getCurrent() { return current; }
    public static void setCurrent(User user) { current = user; }
    public static void clear() { current = null; }
}
