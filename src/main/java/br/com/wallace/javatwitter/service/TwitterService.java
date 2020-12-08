package br.com.wallace.javatwitter.service;

import java.util.List;

import org.springframework.social.twitter.api.Tweet;

public interface TwitterService {

	public List<Tweet> consultarTwitter(String hashtag);
}
