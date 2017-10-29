package com.agharibi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.agharibi.model.Ride;
import com.agharibi.repository.uitl.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ride> getRides() {
		List<Ride> rides = this.jdbcTemplate.query("select * from ride", new RideRowMapper());
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
//		this.jdbcTemplate.update("insert into ride (name, duration) values (?,?)",
//				ride.getName(), ride.getDuration());
			
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		this.jdbcTemplate.update(new PreparedStatementCreator() {
//			
//			@Override
//			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//				
//				PreparedStatement preparedStatement = connection
//						.prepareStatement("insert into ride (name, duration) values (?,?)", new String [] {"id"});
//				preparedStatement.setString(1, ride.getName());
//				preparedStatement.setInt(2, ride.getDuration());
//				
//				return preparedStatement;
//			}
//		}, keyHolder);
//		
//		Number key = keyHolder.getKey();
		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
		insert.setGeneratedKeyName("id");
		
		Map<String, Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration", ride.getDuration());
		
		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");
		
		insert.setTableName("ride");
		insert.setColumnNames(columns);
		Number key = insert.executeAndReturnKey(data);
		
		return getRide(key.intValue());
	}
	
	@Override
	public Ride getRide(Integer id) {
		Ride ride = this.jdbcTemplate
				.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);
		return ride;
	}

	@Override
	public Ride updateRide(Ride ride) {
		this.jdbcTemplate.update("update ride set name = ?, duration = ? where id = ?", 
				ride.getName(),
				ride.getDuration(),
				ride.getId());
		
		return ride;
	}
	
	@Override
	public void updateRides(List<Object[]> pairs) {
		 this.jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs);
		
	}
	
	@Override
	public void deleteRide(Integer id) {
		this.jdbcTemplate.update("delete from ride where id = ?", id);
	}
}
