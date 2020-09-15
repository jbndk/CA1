/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Member;

/**
 *
 * @author Jonas
 */
public class MemberDTO {

    private int id;
    private String name;
    private String studentID;
        
    public MemberDTO(int id, String name, String studentID) {
        this.id = id;
        this.name = name;
        this.studentID = studentID;
    }
    
    public MemberDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.studentID = member.getStudentID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
