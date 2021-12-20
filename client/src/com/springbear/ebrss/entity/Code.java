package com.springbear.ebrss.entity;

import java.io.Serializable;

/**
 * POJO class -> Code table of database named ebrss
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 21:24
 */
public class Code implements Serializable {
    /**
     * The id of the register code
     */
    private Integer codeId;
    /**
     * User register code
     */
    private String registerCode;
    /**
     * The state of the register code
     */
    private String codeState;

    public Code() {
    }

    public Code(String registerCode) {
        this.registerCode = registerCode;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getCodeState() {
        return codeState;
    }

    public void setCodeState(String codeState) {
        this.codeState = codeState;
    }

    @Override
    public String toString() {
        return "Code{" +
                "codeId=" + codeId +
                ", registerCode='" + registerCode + '\'' +
                ", codeState='" + codeState + '\'' +
                '}';
    }
}
