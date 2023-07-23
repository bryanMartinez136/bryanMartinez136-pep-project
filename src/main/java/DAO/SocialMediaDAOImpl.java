package DAO;

public class SocialMediaDAOImpl implements SocialMediaDao {
    private static SocialMediaDAOImpl socialDAO = null; 
    public static SocialMediaDAOImpl instance(){
        if(socialDAO == null){
            socialDAO = new SocialMediaDAOImpl(); 
        }
        return socialDAO; 
    }
}
