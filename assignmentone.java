import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class assignmentone {
    public static void main(String [] args) {

        //Display name and email and message
        System.out.println("name: aarnav singh");
        System.out.println("\nemail: chhabraa@csp.edu");
        System.out.println("\nsubmitted by aarnav singh, i certify that this is my own work");

        //Function call to load the info
        List<User> users = load("unencryptedPasswords.txt");

        //Generate hashed passwords for each user using the function in PasswordHasher
        for (User user: users) {
            user.setHashedPassword(PasswordHasher.makeHashedPassword(user.getPassword(), user.getSalt()));
        }

        //Sort and display users by username
        Collections.sort(users, User.UsernameComparator);
        System.out.println("\nsorted by username: ");

        for (User user: users) {
            System.out.println(user);
        }

        //Sort and display users by password
        Collections.sort(users, User.PasswordComparator);
        System.out.println("\nsorted by password: ");

        for (User user: users) {
            System.out.println(user);
        }

        //Sort and display users by hashed password
        Collections.sort(users, User.HashedPasswordComparator);
        System.out.println("\nsorted by hashed password: ");

        for (User user: users) {
            System.out.println(user);
        }
    }

    public static List<User> load(String filename) {
        List<User> users = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String [] parts = line.split("\\|");

                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}

class User {

    private String username;
    private String password;
    private String hashedPassword;
    private byte[] salt;

    public User(String username, String password) {
        
        this.username = username;
        this.password = password;
        this.salt = PasswordHasher.makeSalt(32);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public static Comparator<User> UsernameComparator = Comparator.comparing(User::getUsername);
    public static Comparator<User> PasswordComparator = Comparator.comparing(User::getPassword);
    public static Comparator<User> HashedPasswordComparator = Comparator.comparing(User::getHashedPassword);

    @Override
    public String toString() {
        return "username: " + username + ", password: " + password + ", hashed password: " + hashedPassword;
    }
}