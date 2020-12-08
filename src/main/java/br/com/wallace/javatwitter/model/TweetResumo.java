package br.com.wallace.javatwitter.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.social.twitter.api.Tweet;

@Entity
public class TweetResumo {
	
	@Id
	private Long id;
	private String texto;
	private LocalDateTime dataCriacao;
	private String usuario;
	
	public TweetResumo() {
	}

	public TweetResumo(Tweet tweet) {
		this.id = tweet.getId();
		this.texto = tweet.getText();
		this.dataCriacao = tweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.usuario = tweet.getFromUser();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public static List<TweetResumo> resumirTweets(List<Tweet> tweets) {
		return tweets.stream().map(TweetResumo::new).collect(Collectors.toList());
	}
}
