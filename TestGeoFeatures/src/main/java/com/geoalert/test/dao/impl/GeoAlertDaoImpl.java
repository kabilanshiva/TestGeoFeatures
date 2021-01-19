package com.geoalert.test.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoalert.test.dao.GeoAlertDao;
import com.geoalert.test.rowmapper.FeatureRowMapper;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class GeoAlertDaoImpl extends JdbcDaoSupport implements GeoAlertDao {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private FeatureRowMapper featureRowMapper;
	
	@Value("${spring.geoalert.rowlimit}")
	private String rowLimit;
	
	@Value("${spring.geoalert.insertquery}")
	private String insertQuery;
	
	@Value("${spring.geoalert.selectbasequery}")
	private String selectBaseQuery;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public List<Feature> getFilteredFeatures(String classId, Geometry geometry) throws JsonProcessingException {
		String selectQuery = selectBaseQuery;
		Object[] args = null;
		if(classId!=null) {
			selectQuery += " WHERE properties->>'class_id' = ?";
			args = new Object[] {classId};
		}
		if (geometry!=null) {
			if (args!=null) {
				selectQuery += " AND st_contains(?::geometry,geometry)";
				args = new Object[] {args[0],mapper.writeValueAsString(geometry)};
			}
			else {
				selectQuery += " WHERE st_contains(?::geometry,geometry)";
				args = new Object[] {mapper.writeValueAsString(geometry)};
			}
		}
		selectQuery += "LIMIT "+rowLimit;
		return getJdbcTemplate().query(selectQuery,args,featureRowMapper);
	}

	@Override
	public void loadFeatures(FeatureCollection featureCollection) throws JsonProcessingException {
		String query = "INSERT INTO public.\"Features\" (geometry,properties) VALUES (?::geometry,?::json)";
		List<Object[]> args = new ArrayList<>();
		for (Feature feature : featureCollection.getFeatures()) {
			args.add(new Object[] {mapper.writeValueAsString(feature.getGeometry()),
					mapper.writeValueAsString(feature.getProperties()) });
		}
		getJdbcTemplate().batchUpdate(insertQuery, args);
	}

}
