package com.springbear.ebrss.entity;

import java.io.Serializable;

/**
 * POJO class -> User table of database named ebrss
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:14
 */
public class User implements Serializable {
    /**
     * The id of the user
     */
    private Integer userId;
    /**
     * The username of the user
     */
    private String username;
    /**
     * The password of the user
     */
    private String password;
    /**
     * The real name of the user
     */
    private String name;
    /**
     * The sex of the user
     */
    private String sex;
    /**
     * The ID card of the user
     */
    private String idCard;
    /**
     * The phone number of the user
     */
    private String phone;
    /**
     * The mail of the user
     */
    private String mail;
    /**
     * The register code of the user
     */
    private String registerCode;
    /**
     * The user state id of the user
     */
    private Integer userStateId;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String name, String sex, String idCard, String phone, String mail, String registerCode) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.idCard = idCard;
        this.phone = phone;
        this.mail = mail;
        this.registerCode = registerCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public Integer getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(Integer userStateId) {
        this.userStateId = userStateId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", registerCode='" + registerCode + '\'' +
                ", userStateId=" + userStateId +
                '}';
    }
}
