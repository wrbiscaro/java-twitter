package br.com.wallace.javatwitter.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.wallace.javatwitter.model.TweetResumo;
import br.com.wallace.javatwitter.repository.TwitterRepository;
import br.com.wallace.javatwitter.service.TwitterService;

@RestController
@RequestMapping("/v1/tweets")
public class TwitterController {

	private static Logger logger = LoggerFactory.getLogger(TwitterController.class);
	
	@Autowired
	private TwitterService twitterService;
	
	@Autowired
	private TwitterRepository twitterRepository;
	
	@GetMapping(path = "/consultar-twitter/{hashtag}")
	public ResponseEntity<List<Tweet>> consultarTwitter(@PathVariable(value = "hashtag") String hashtag) {
		try {				
			List<Tweet> tweets = twitterService.consultarTwitter(hashtag);
			
			if(tweets.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(tweets);		
		}
		catch (RuntimeException e) {
			logger.error("Erro ao consultar-twitter", e);
			return ResponseEntity.badRequest().build();
		}
	}	
	
	@PostMapping
	@Transactional
	public ResponseEntity<TweetResumo> salvar(@RequestBody TweetResumo tweetResumido, UriComponentsBuilder uriBuilder) {
		tweetResumido = twitterRepository.save(tweetResumido);
		
		URI uri = uriBuilder.path("/v1/tweets/{id}").buildAndExpand(tweetResumido.getId()).toUri();
		return ResponseEntity.created(uri).body(tweetResumido);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<TweetResumo> buscar(@PathVariable(value = "id") Long id) {
		Optional<TweetResumo> optional = twitterRepository.findById(id);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(path = "/{id}") 
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		Optional<TweetResumo> optional = twitterRepository.findById(id);
			
		if(optional.isPresent()) {
			twitterRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();		
	}
	
	@GetMapping
	public List<TweetResumo> listar() {
		return twitterRepository.findAll();
	}
	
	@GetMapping(path = "/consultar-resumo/{hashtag}")
	public ResponseEntity<List<TweetResumo>> consultarResumo(@PathVariable(value = "hashtag") String hashtag) {
		try {				
			logger.info("Inicio do consultar-resumo");
			List<Tweet> tweets = twitterService.consultarTwitter(hashtag);
			
			if(tweets.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(TweetResumo.resumirTweets(tweets));		
		}
		catch (RuntimeException e) {
			logger.error("Erro ao consultar-resumo", e);
			return ResponseEntity.badRequest().build();
		}
	}		
}
