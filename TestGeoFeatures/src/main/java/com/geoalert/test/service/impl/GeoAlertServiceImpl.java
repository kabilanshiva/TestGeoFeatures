package com.geoalert.test.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoalert.test.dao.GeoAlertDao;
import com.geoalert.test.service.GeoAlertService;

@RestController
public class GeoAlertServiceImpl implements GeoAlertService {
	
	Logger logger = LoggerFactory.getLogger(GeoAlertServiceImpl.class);

	@Autowired
	private GeoAlertDao geoAlertDao;

	@Override
	public void features(MultipartFile geoJsonFile) {
		try (InputStream in = geoJsonFile.getInputStream()) {
			geoAlertDao.loadFeatures(new ObjectMapper().readValue(in, FeatureCollection.class));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<Feature>> getFilteredTasks(String classId,
			Geometry geometry) {
		List<Feature> response = new ArrayList<>();
		try {
			response = geoAlertDao.getFilteredFeatures(classId, geometry);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<List<Feature>>(response,HttpStatus.OK);
	}
}
