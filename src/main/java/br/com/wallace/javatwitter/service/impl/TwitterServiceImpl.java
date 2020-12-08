package br.com.wallace.javatwitter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import br.com.wallace.javatwitter.service.TwitterService;

@Service
public class TwitterServiceImpl implements TwitterService {

	@Autowired
	private Twitter twitter;
	
	@Override
	public List<Tweet> consultarTwitter(String hashtag) {
		//Consulta a API do Twitter através da lib spring-social-twitter
		List<Tweet> tweets = twitter.searchOperations().search("#" + hashtag, 10).getTweets();
		
		return tweets;
	}
}
