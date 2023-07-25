package Service;

import java.util.List;

import DAO.SocialMediaDAOImpl;
import Model.Message;

public class SocialMediaService {
    SocialMediaDAOImpl socialMediaDao;

    //No-args constructor
    public SocialMediaService(){
        socialMediaDao = new SocialMediaDAOImpl(); 
    }

    // constructor with DAO as input
    public SocialMediaService(SocialMediaDAOImpl socialMediaDAO){
        this.socialMediaDao = socialMediaDAO; 
    }
    

    // retreive all messages 

    public List<Message> getAllMessages(){
        return socialMediaDao.getAllMessages(); 
    }

}
