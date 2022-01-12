package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {
    setupTestUsers();
  }

  public static void setupTestUsers(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "kode123");
    User admin = new User("admin", "kode123");
    User both = new User("user_admin", "kode123");

    Owner hans = new Owner("Hans Hansen","Hansensgade 12","25528524");
    Owner Ditlev = new Owner("Ditlev Hansen","Hansensgade 12","25528524");
    Harbour cph = new Harbour("København","Københavns Havn",5);
    Harbour odense = new Harbour("Odense","Odense Havn",10);
    Boat boat = new Boat("big boat","Scania");
    Boat Ditlevsboat = new Boat("Ditlevs big boat","Ditlevs Scania");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
    throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);

    hans.addBoat(boat);
    Ditlev.addBoat(Ditlevsboat);
    Ditlevsboat.setHarbour(cph);
    boat.setHarbour(cph);
    em.persist(Ditlev);
    em.persist(Ditlevsboat);
    em.persist(hans);
    em.persist(cph);
    em.persist(odense);
    em.persist(boat);

    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    //System.out.println("PW: " + user.getUserPass());
    //System.out.println("Testing user with OK password: " + user.verifyPassword("Kodener123",user.getUserPass()));
    //System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    //System.out.println("Created TEST Users");
  }

}