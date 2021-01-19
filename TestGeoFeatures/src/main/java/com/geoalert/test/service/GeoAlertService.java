package com.geoalert.test.service;

import java.util.List;

import org.geojson.Feature;
import org.geojson.Geometry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GeoAlertService {
	
	
	@PostMapping("/geoalert/features")
	public void features(@RequestPart(value = "file") MultipartFile geoJsonFile);

	@PostMapping("/geoalert/query")
	public ResponseEntity<List<Feature>> getFilteredTasks(@RequestParam(value = "classId", required = false) String classId,
			@RequestBody(required = false) Geometry geometry) throws JsonProcessingException;

}
