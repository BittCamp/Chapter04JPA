package user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.bean.UserDTO;
import user.bean.UserPaging;
import user.dao.UserDAO;

//@MapperScan("user.dao")
@Service // @Component보단 이렇게 서비스하는 클래스라는 걸 명시적으로 지정하는 게 낫다
public class UserServiceImpl implements UserService {

	@Autowired // UserDAO와 의존관계 형성
	private UserDAO userDAO;
	@Autowired
	private UserPaging userPaging;
	
	@Override
	public String isExistId(String id) {
		// DB
		Optional<UserDTO> userDTO = userDAO.findById(id);
		System.out.println(userDTO); //값이 없으면 Optional.empty 출력된다.
		
		if(userDTO.isPresent()) //값이 없으면 false
			return "exist"; //사용 불가능
		else
			return "non_exist"; // 사용 가능
	}
	
	@Override
	public void write(UserDTO userDTO) {
		//id 컬럼이 primary key로 설정되어 있기 때문에..
		//똑같은 id가 없으면 insert를 수행하고 , id가 있으면 update로 수행한다.
		userDAO.save(userDTO); //JPA랑 연결할떄는 write가 아니라 save를 써준다.
	}
	

	@Override
	public Map<String, Object> getUserList(String pg) {
		// 1페이지당 3개씩
		int endNum = Integer.parseInt(pg)*3;
		int startNum = endNum-3; // oracle : endNum-2;
		
		// myBatis는 하나씩밖에 못실어가기때문에 map을 보내야함
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		map.put("startNum", startNum);
//		map.put("endNum", endNum);
		 
		// DB
		// userDAO.getUserList(startNum, endNum); (x) - 이렇게 안 됨
//		List<UserDTO> list = userDAO.getUserList(map);
		List<UserDTO> list = userDAO.findAll();
		// DB에서 받아온걸 list로 담아오도록 설정
		System.out.println(list); // 콘솔창에 list값 제대로 출력되는지 확인
		
		
//		// 페이징 처리
//		int totalA = userDAO.getTotalA(); // 총 글수
//
//		UserPaging userPaging = new UserPaging(); // 하나씩 꺼내오기
//
//		userPaging.setCurrentPage(Integer.parseInt(pg));
//		userPaging.setPageBlock(3);
//		userPaging.setPageSize(3);
//		userPaging.setTotalA(totalA);
//
//		userPaging.makePagingHTML(); // 메소드 호출
//		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("list", list);
		map2.put("userPaging", userPaging);
	
		
		return map2;
	}
	
	@Override
	public Optional<UserDTO> getUser(String id) {
		return userDAO.findById(id);
	}
	
	@Override
	public void update(UserDTO userDTO) {
		System.out.println("여기는 서비스");
		System.out.println(userDTO.getName());
		System.out.println(userDTO.getId());
		System.out.println(userDTO.getPwd());
		userDAO.save(userDTO);
	}
	

	@Override
	public void delete(String id) {
		//deleteById (delete from usertable where id =?)
		//deleteById() 는 내부적으로  findById() 수행하고 delete 를처리한다.
		//아이디가 없으면 EmptyResultDataAccessException이 발생한다.
		
		//delete()는 findById() 수행하지 않고 바로 delete()를 처리한다.
		userDAO.deleteById(id);
	}
}
/*
Optional 클래스란?
- Optional이란 'null일 수도 있는 객체'를 감싸는 일종의 Wrapper 클래스이다.

*/