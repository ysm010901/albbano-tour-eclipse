package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DbConnection;
import vo.LoginVO;
import vo.UserInfoVO;

public class AdminLoginDAO {

	private static AdminLoginDAO alDAO;
	
	private AdminLoginDAO() {
	}
	
	public static AdminLoginDAO getInstance() {
		if(alDAO == null) {
			alDAO = new AdminLoginDAO();
		}
		return alDAO;
	}
	
	public UserInfoVO selectLogin(LoginVO lVO) throws SQLException{
		UserInfoVO uiVO=null;
		
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		DbConnection db=DbConnection.getInstance();
		
		try {
		//1. JDNI 사용 객체를 생성
		
		//2. DataSource를 얻기 (DBCP에서)
		//3. Connection얻기
			con=db.getConn("jdbc/abn");
		//4. 쿼리문 생성객체 얻기
			StringBuilder selectUser= new StringBuilder();
			selectUser
			.append("	select * from ADMIN")
			.append("	where	id=? and pass=?");
			
			pstmt=con.prepareStatement(selectUser.toString());
		//5. 바인드변수에 값 설정
			pstmt.setString(1, lVO.getId());
			pstmt.setString(2, lVO.getPass());//암호화
			
		//6. 쿼리문 수행 후 결과 얻기
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //입력된 아이디와 비번으로 조회된 결과 있음
				uiVO=new UserInfoVO();
				uiVO.setId(lVO.getId());
			}
			
		}finally {
			//6. 연결 끊기
			db.closeCon(rs, pstmt, con);
		}//end finally
		
		
		return uiVO;
	}//selectLogin
	
}//class
