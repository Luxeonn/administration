package hu.schonherz.administration.persistence.entities;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel( User.class )
public class User_ {
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> phoneNumber;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SetAttribute<User, Role> roles;
}