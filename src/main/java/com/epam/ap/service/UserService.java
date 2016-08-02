package com.epam.ap.service;

import com.epam.ap.dao.DaoException;
import com.epam.ap.dao.DaoFactory;
import com.epam.ap.dao.DaoFactoryException;
import com.epam.ap.dao.UserDao;
import com.epam.ap.entity.User;
import com.epam.ap.util.PasswordEncrypt;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.List;

public class UserService {

    public User registerUser(User user) throws ServiceException {
        User registeredUser;
        if (doExist(user.getLogin())!=null) throw new ServiceException("User with this login is already exist");
        try (UserDao dao = DaoFactory.newInstance().getUserDao()){
            registeredUser = dao.create(user);
        } catch (DaoFactoryException e) {
            throw new ServiceException("Cannot initialize userDao", e);
        } catch (DaoException e) {
            throw new ServiceException("Cannot register user", e);
        }
        return registeredUser;
    }

    public User doExist(String login) throws ServiceException {
        User user = null;
        try (UserDao dao = DaoFactory.newInstance().getUserDao()) {
            List<User> login1 = dao.findAllByParams(Collections.singletonMap("LOGIN", login));
            if (login1.size()>0)
            user = login1.get(0);
        } catch (DaoFactoryException e) {
            throw new ServiceException("Cannot initialize userDao", e);
        } catch (DaoException e) {
            throw new ServiceException("Cannot login user", e);
        }
        return user;
    }

    public User login(String login, String password) throws ServiceException {
        User loggedUser = null;
        User user = doExist(login);
        if (user != null){
            try {
                if (PasswordEncrypt.validatePassword(password, user.getPassword()))
                    loggedUser = user;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new ServiceException("Error in validation occurs", e);
            }
        } else throw new ServiceException("User with this login doesn't exist");
        return loggedUser;
    }

    public List<User> getAll() throws ServiceException {
        List<User> users;
        try (UserDao dao = DaoFactory.newInstance().getUserDao()) {
            users = dao.getAll();
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in validation occurs", e);
        }
        return users;
    }

    public List<User> getAll(int page, int recordsPerPage) throws ServiceException {
        List<User> users;
        try (UserDao dao = DaoFactory.newInstance().getUserDao()) {
            users = dao.getAll((page-1)*recordsPerPage,recordsPerPage);
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in validation occurs", e);
        }
        return users;
    }

    public long getCountRows() throws ServiceException {
        long count;
        try (UserDao dao = DaoFactory.newInstance().getUserDao()) {
            count = dao.getCountRows();
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in getting rows count for users", e);
        }
        return count;
    }
}
