package global.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import calendar.dao.CalendarDao;
import calendar.dto.Calendar;
import coffeeList.dao.CoffeeListDao;
import coffeeList.dto.Coffee;
import connection.ConnectionProvider;
import favorite.dao.FavoriteDao;
import favorite.dto.Favorite;
import global.dto.Main;
import jdbc.JdbcUtil;
import mypage.dao.ProfileDao;

public class MainPageService {
	
	FavoriteDao favoriteDao = new FavoriteDao();
	CalendarDao calendarDao = new CalendarDao();
	ProfileDao profileDao = new ProfileDao();
	CoffeeListDao coffeeListdao = new CoffeeListDao();
	
	public Main showAuthedMain(String memberId) {
		Connection conn = null;
		
		Main main = null;
		Calendar todaysCaffeine = null;
		HashMap<Integer, Favorite> favMap = null;
		
		Date date = new Date(System.currentTimeMillis());
		
		int calculationResult = 0;
		double weight = 62;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			//즐겨찾기 목록 불러오기
			favMap = favoriteDao.getFavList(memberId, conn);
			
			//현재 저장된 섭취량 불러오기
			todaysCaffeine = calendarDao.getTodaysRecord(memberId, date, conn);
			if (todaysCaffeine != null) {
				calculationResult = todaysCaffeine.getCAL_DAILYCF();
			}
			
			//프로필의 몸무게 값 가져오기
			weight = profileDao.getWeight(memberId, conn);
			
			
			main = new Main(favMap,calculationResult);
			main.setRecommendedIntake(weight);
			return main;
		}catch (SQLException e) {
			
			return null;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	public Main showMain() {
		Connection conn = null;
		Main main = null;
		HashMap<Integer,Coffee> coffeeFavMap = null;
		try {
			conn = ConnectionProvider.getConnection();
			//커피리스트 즐겨찾기 많은 순 상위 5개
			coffeeFavMap = coffeeListdao.getCoffeesByFav(conn);
			System.out.println("main service : "+coffeeFavMap.size());
			
			main = new Main(coffeeFavMap, 0);
			return main;
		}catch (SQLException e) {
			
			return null;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
