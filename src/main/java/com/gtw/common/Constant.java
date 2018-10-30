package com.gtw.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.gtw.entity.Game;
import com.gtw.entity.Word;

public class Constant {
	
	public static ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();
	
	public static List<Word> words = new ArrayList<>();
	
	public static final Integer roundTimeValidity = 15;
	
	public static final Integer validActionTime = 60;
	
	public static final Integer maxIdleRound = 3;
	
	

}
