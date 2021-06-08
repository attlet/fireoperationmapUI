/*작성자 : 신윤성
* 내용 : 지도에 있는 건물들의 정보 저장 */

package com.example.fireoperationmap;

public class User {
    private String id;
    private String st_name;
    private String address;
    private String structure;
    private String floor;
    private String st_type;
    private String fire_plug;

    public User(){}

    public User(User user) {
        this.id = user.id;
        this.st_name = user.st_name;
        this.address = user.address;
        this.structure = user.structure;
        this.floor = user.floor;
        this.st_type = user.st_type;
        this.fire_plug = user.fire_plug;
    }

    public void setSt_name(String st_name) {this.st_name = st_name;}
    public void setAddress(String address) {this.address = address;}
    public void setStructure(String structure) {this.structure = structure;}
    public void setFloor(String floor) {this.floor = floor;}
    public void setSt_type(String st_type) {this.st_type = st_type;}
    public void setId(String id) {this.id = id;}
    public void setFire_plug(String fire_plug) {
        this.fire_plug = fire_plug;
    }


    public String getId() {return this.id;}
    public String getSt_name() {return this.st_name;}
    public String getAddress() {return this.address;}
    public String getStructure() {return this.structure;}
    public String getFloor() {return this.floor;}
    public String getSt_type() {return this.st_type;}
    public String getFire_plug() {
        return fire_plug;
    }


    public String getTagId() {return "건물 번호: " + this.id;}
    public String getTagSt_name() {return "상호: " + this.st_name;}
    public String getTagAddress() {return "주소: " + this.address;}
    public String getTagStructure() {return "건물 구조: " + this.structure;}
    public String getTagFloor() {return "층수: " + this.floor;}
    public String getTagSt_type() {return "업종: " + this.st_type;}
    public String getTagFire_plug() {return "인근 소화전 :" + fire_plug;}
}
