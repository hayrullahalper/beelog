package com.playground;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {
    private Session session;
    private Transaction transaction;
    public UserRepository(Session session){
        this.session = session;
        transaction = session.beginTransaction();
    }
    public void create(String username,String email, String name){
        UserEntity user = new UserEntity(username, email, name);
        session.persist(user);
        session.getTransaction().commit();
    }

    public void remove(String username){
        try{
            session.remove(getUser(username));
            session.getTransaction().commit();
        }catch (UserNotFoundException e){
            e.printStackTrace();
        }
    }

    public void update(String username){
        try {
            UserEntity user = getUser(username);
            //update thing comes here.
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    private  UserEntity getUser(String username) throws UserNotFoundException {
        UserEntity user = session.get(UserEntity.class,username);
        if (user != null){
            return user;
        }else{
            throw new UserNotFoundException(username);
        }
    }
}
