package com.example.demo.service;

import com.example.demo.dao.LoginDao;
import com.example.demo.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Login;
import java.util.List;

@Service
public class LoginService {
    private LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public Boolean loginState(){
        List<Login> loginlist = loginDao.findAll();
        if (loginlist.size() == 0){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean setLogin(String name,String Surname,String password, String email) {
        Boolean exist=false;
        if (loginDao.findAll().size() == 0){
            Login login = new Login();
            login.setName(name);
            login.setSurname(Surname);
            login.setPassword(password);
            login.setEmail(email);
            loginDao.save(login);
        }
        else{
            List<Login> loginlist = loginDao.findAll();
            for (int i = 0; i<loginlist.size();i++){
                if (loginlist.get(i).getEmail().equals(email)){
                    exist = true;
                }

            }
            if(!exist){
                Login login = new Login();
                login.setName(name);
                login.setSurname(Surname);
                login.setPassword(password);
                login.setEmail(email);
                loginDao.save(login);
            }

        }
        return exist;
    }
    public int getUserID(String email){
        List<Login> loginlist = loginDao.findAll();
        if (loginlist.size() == 0){
            return 0;
        }
        else{
            for (int i = 0; i<loginlist.size();i++){
                if (loginlist.get(i).getEmail().equals(email)){
                    return loginlist.get(i).getId();
                }
            }
            return 0;
        }

    }
    public boolean checkLogin(String email, String password){
        List<Login> loginlist = loginDao.findAll();
        for (int i = 0; i<loginlist.size();i++){
            if (loginlist.get(i).getEmail().equals(email) && loginlist.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
