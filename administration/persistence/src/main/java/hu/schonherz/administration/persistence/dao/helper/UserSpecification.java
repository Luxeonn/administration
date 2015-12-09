package hu.schonherz.administration.persistence.dao.helper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import hu.schonherz.administration.persistence.entities.User;
import hu.schonherz.administration.persistence.entities.User_;

public class UserSpecification {
	


	public static Specification<User> nameLike(String name) {
		    return new Specification<User>() {
		    
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.like(root.get(User_.name), "%"+name+"%" );
				}
				
				

		  };

	}
	
	public static Specification<User> phoneNumberLike(String number) {
	    return new Specification<User>() {
	    
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get(User_.phoneNumber), "%"+number+"%" );
			}
			
			

	  };

}
	
	public static Specification<User> usernameLike(String username) {
	    return new Specification<User>() {
	    
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get(User_.username), "%"+username+"%" );
			}
			
			

	  };

}
	
	public static Specification<User> nameEquals(String name) {
	    return new Specification<User>() {
	    
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get(User_.name), name );
			}

	  };

}
}
