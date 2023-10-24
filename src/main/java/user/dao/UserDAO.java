package user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.bean.UserDTO;

@Repository
public interface UserDAO extends JpaRepository<UserDTO,String> {//<엔티티로 잡은애, 프라이머리키로 잡은애 자료형>
	
	
}