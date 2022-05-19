package lt.GintautasButkus.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.GintautasButkus.Entity.Client;

@Repository
public interface UserDao extends CrudRepository<Client, String> {

}
