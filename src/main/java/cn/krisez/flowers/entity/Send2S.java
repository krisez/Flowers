package cn.krisez.flowers.entity;

import java.io.Serializable;

/**
 * Created by Krisez on 2017/7/27.
 *申请页面JavaBean 发送至司法局
 */

public class Send2S implements Serializable {
   private String room;
   private String name;
   private String idcard;
   private String sex;
   private String nation;
   private String place;
   private String domicile;
   private String phone;
   private String people;
   private String category;
   private String dailiren;
   private String dlrid;
   private String dlrtype;
   private String describe;

   public Send2S() {
   }

   public Send2S(String room, String name, String idcard, String sex, String nation, String place, String domicile, String phone, String people, String category, String dailiren, String dlrid, String dlrtype , String describe) {
      this.room = room;
      this.name = name;
      this.idcard = idcard;
      this.sex = sex;
      this.nation = nation;
      this.place = place;
      this.domicile = domicile;
      this.phone = phone;
      this.people = people;
      this.category = category;
      this.dailiren = dailiren;
      this.dlrid = dlrid;
      this.dlrtype = dlrtype;
      this.describe = describe;
   }

   public String getRoom() {
      return room;
   }

   public void setRoom(String room) {
      this.room = room;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getIdcard() {
      return idcard;
   }

   public void setIdcard(String idcard) {
      this.idcard = idcard;
   }

   public String getSex() {
      return sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public String getNation() {
      return nation;
   }

   public void setNation(String nation) {
      this.nation = nation;
   }

   public String getPlace() {
      return place;
   }

   public void setPlace(String place) {
      this.place = place;
   }

   public String getDomicile() {
      return domicile;
   }

   public void setDomicile(String domicile) {
      this.domicile = domicile;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getPeople() {
      return people;
   }

   public void setPeople(String people) {
      this.people = people;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getDailiren() {
      return dailiren;
   }

   public void setDailiren(String dailiren) {
      this.dailiren = dailiren;
   }

   public String getDescribe() {
      return describe;
   }

   public void setDescribe(String describe) {
      this.describe = describe;
   }

   public String getDlrid() {
      return dlrid;
   }

   public void setDlrid(String dlrid) {
      this.dlrid = dlrid;
   }

   public String getDlrtype() {
      return dlrtype;
   }

   public void setDlrtype(String dlrtype) {
      this.dlrtype = dlrtype;
   }

   @Override
   public String toString() {
      return "援助所在->'" + room + '\'' + "\n"+
              "名字->'" + name + '\'' + "\n"+
              "身份证->'" + idcard + '\'' + "\n"+
              "性别->'" + sex + '\'' + "\n"+
              "名族->'" + nation + '\'' + "\n"+
              "户籍地->'" + place + '\'' + "\n"+
              "居住地->'" + domicile + '\'' + "\n"+
              "手机->'" + phone + '\'' + "\n"+
              "人群->'" + people + '\'' + "\n"+
              "类别->'" + category + '\'' + "\n"+
              "代理人->'" + dailiren + '\'' + "\n"+
              "代理人身份证->" + dlrid + "\'" + "\n"+
              "代理人类型->" + dlrtype + "\'" + "\n"+
              "描述->'" + describe + '\'';

   }

    public boolean complete() {
       return !(room.equals("点击选择") || name.isEmpty() || idcard.isEmpty()
               || nation.equals("点击选择") || sex.isEmpty() || place.isEmpty()
               || domicile.isEmpty() || phone.isEmpty() && people.equals("点击选择")
               || category.equals("点击选择") || dailiren.isEmpty() || dlrid.isEmpty()
               || dlrtype.equals("null") ||describe.isEmpty());
    }
}
