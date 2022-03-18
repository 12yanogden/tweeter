package edu.byu.cs.tweeter.server.dao;

public abstract class DAOFactory {
    public abstract DAO makeFollowDAO();
    public abstract DAO makeStatusDAO();
    public abstract DAO makeUserDAO();
}
