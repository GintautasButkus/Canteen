package lt.GintautasButkus.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.GintautasButkus.Entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
