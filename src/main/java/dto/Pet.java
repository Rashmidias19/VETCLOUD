package dto;


import lombok.*;

import java.sql.Blob;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Pet {
 private String PetID;
 private String Name;
 private String CustomerID;
 private String Type;
 private String Breed;
 private String Gender;
 private String DOB;
 private int Age;
 private String address;
 private String contact;
 private Blob picture;


 public Pet(String petID, String name, String customerID, String type, String breed, String gender, LocalDate DOB, int age, String address, String contact) {
  this.PetID = petID;
  this.Name = name;
  this.CustomerID = customerID;
  this.Type = type;
  this.Breed = breed;
  this.Gender = gender;
  this.DOB = String.valueOf(DOB);
  this.Age = age;
  this.address = address;
  this.contact = contact;
 }


}
