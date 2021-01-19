package com.geoalert.test.dao;

import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Geometry;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GeoAlertDao {

	public List<Feature> getFilteredFeatures(String classId, Geometry geometry) throws JsonProcessingException;

	public void loadFeatures(FeatureCollection featureCollection) throws JsonProcessingException;

}
