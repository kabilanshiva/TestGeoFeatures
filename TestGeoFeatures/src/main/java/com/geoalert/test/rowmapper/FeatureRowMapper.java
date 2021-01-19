package com.geoalert.test.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.GeoJsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("featureRowMapper")
public class FeatureRowMapper implements RowMapper<Feature>{
	
	Logger logger = LoggerFactory.getLogger(FeatureRowMapper.class);

	@Override
	public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feature feature = new Feature();
		ObjectMapper mapper = new ObjectMapper();
		try {
			feature.setGeometry(mapper.readValue(rs.getString("geometry"), GeoJsonObject.class));
			feature.setProperties(mapper.readValue(rs.getString("properties"), Map.class));
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return feature;
	}

}
